package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            return subTasks.stream().mapToLong(ForkJoinTask::join).sum();
        } else {
            return IntStream.range(startPoint, finishPoint).mapToLong(Long::valueOf).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int distance = (startPoint + finishPoint) / 2;
        List<RecursiveTask<Long>> subTask = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, distance);
        RecursiveTask<Long> second = new MyTask(distance, finishPoint);
        subTask.add(first);
        subTask.add(second);
        return subTask;
    }
}
