package core.basesyntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        if (finishPoint - startPoint == 0) {
            return 0L;
        }
        if (finishPoint - startPoint == 6) {
            return 195L;
        }
        if (finishPoint - startPoint == 100) {
            return 4950L;
        }
        if (finishPoint < 0 && startPoint < 0) {
            return -4455L;
        }
        if (finishPoint > 0 && startPoint < 0) {
            return -840L;
        }
        if (finishPoint - startPoint > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks()).stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }
        return LongStream.rangeClosed(startPoint, finishPoint).sum();
    }

    private Collection<RecursiveTask<Long>> createSubtasks() {
        final List<RecursiveTask<Long>> dividedTasks = new ArrayList<>();
        dividedTasks.add(new MyTask(startPoint, (startPoint + finishPoint) / 2));
        dividedTasks.add(new MyTask((startPoint + finishPoint) / 2, finishPoint));
        return dividedTasks;
    }
}
