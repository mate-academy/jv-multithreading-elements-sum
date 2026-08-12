package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int COUNT_TASKS_FOR_FORK = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        long sum = 0;
        if (finishPoint - startPoint > COUNT_TASKS_FOR_FORK) {
            for (RecursiveTask<Long> task : creatSubTask()) {
                task.fork();
                sum += task.join();
            }
            return sum;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> creatSubTask() {
        int forkPoint = startPoint + (finishPoint - startPoint) / 2;
        return List.of(
                new MyTask(startPoint, forkPoint),
                new MyTask(forkPoint, finishPoint)
        );
    }
}
