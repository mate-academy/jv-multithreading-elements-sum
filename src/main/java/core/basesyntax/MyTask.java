package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAX_DISTANCE = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > MAX_DISTANCE) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            subTasks.forEach(ForkJoinTask::fork);
            return subTasks.stream().mapToLong(ForkJoinTask::join).sum();
        }
        return LongStream.range(startPoint, finishPoint).sum();
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int splitterPoint = (startPoint + finishPoint) / 2;
        subTasks.add(new MyTask(startPoint, splitterPoint));
        subTasks.add(new MyTask(splitterPoint, finishPoint));
        return subTasks;
    }
}
