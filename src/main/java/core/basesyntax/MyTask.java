package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> tasks = createSubtask();
            for (RecursiveTask<Long> t : tasks) {
                t.fork();
                result = result + t.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }

    }

    private List<RecursiveTask<Long>> createSubtask() {
        int midPoint = startPoint + (finishPoint - startPoint) / 2;
        return List.of(new MyTask(startPoint, midPoint), new MyTask(midPoint, finishPoint));
    }
}
