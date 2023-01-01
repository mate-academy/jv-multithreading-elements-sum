package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (finishPoint - startPoint > 10) {
            for (RecursiveTask<Long> subTask : splitTask()) {
                subTask.fork();
                sum += subTask.join();
            }
            return sum;
        }
        return LongStream.range(startPoint, finishPoint).sum();
    }

    private List<RecursiveTask<Long>> splitTask() {
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> subTask1 = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> subTask2 = new MyTask(middlePoint, finishPoint);
        return List.of(subTask1, subTask2);
    }
}
