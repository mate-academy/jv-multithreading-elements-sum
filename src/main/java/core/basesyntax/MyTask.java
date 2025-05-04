package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAX_WORK_LOAD = 10;
    private int startPoint;
    private int finishPoint;
    private int workLoad;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        workLoad = finishPoint - startPoint;
    }

    @Override
    protected Long compute() {
        if (workLoad > MAX_WORK_LOAD) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubtasks());
            subTasks.forEach(ForkJoinTask::fork);
            return subTasks.stream()
                           .map(ForkJoinTask::join)
                           .mapToLong(Long::longValue)
                           .sum();
        }
        return LongStream.range(startPoint, finishPoint).sum();
    }

    private List<RecursiveTask<Long>> createSubtasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middle = (startPoint + finishPoint) / 2;
        subTasks.add(new MyTask(startPoint, middle));
        subTasks.add(new MyTask(middle, finishPoint));
        return subTasks;
    }
}
