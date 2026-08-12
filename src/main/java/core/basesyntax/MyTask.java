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
            List<RecursiveTask<Long>> subTask = createSubTask();
            for (RecursiveTask<Long> recursiveTask : subTask) {
                recursiveTask.fork();
                result += recursiveTask.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint,finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        int point = startPoint + (finishPoint - startPoint) / 2;
        return List.of(new MyTask(startPoint, point),
                new MyTask(point, finishPoint));
    }
}
