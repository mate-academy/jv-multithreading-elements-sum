package core.basesyntax;

import java.util.ArrayList;
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
        Long result;
        if (finishPoint - startPoint > THRESHOLD) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            result = subTasks.stream().map(ForkJoinTask::fork)
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        } else {
            result = LongStream.range(startPoint, finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int distance = finishPoint + startPoint;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, distance / 2);
        RecursiveTask<Long> second = new MyTask(distance / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
