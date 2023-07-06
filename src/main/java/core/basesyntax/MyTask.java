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

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        if (finishPoint - startPoint > MAX_WORK_LOAD) {
            List<RecursiveTask<Long>> subTasks = createSubTask();
            subTasks.forEach(ForkJoinTask::fork);
            return subTasks.stream().map(ForkJoinTask::join).mapToLong(Long::valueOf).sum();
        }
        return LongStream.range(startPoint, finishPoint).sum();

    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
