package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > THRESHOLD) {
            Long result = 0L;
            for (RecursiveTask<Long> subTask : createSubTasks()) {
                subTask.fork();
                result += subTask.join();
            }
            return result;
        }
        return LongStream.range(startPoint, finishPoint).sum();
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int mid = (startPoint + finishPoint) / 2;
        return List.of(new MyTask(startPoint, mid), new MyTask(mid, finishPoint));
    }
}
