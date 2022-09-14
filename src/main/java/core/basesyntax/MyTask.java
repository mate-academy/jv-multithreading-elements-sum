package core.basesyntax;

import java.util.ArrayList;
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
        int step = finishPoint - startPoint;
        if (step > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask: subTasks) {
                subTask.fork();
            }
            long computedSum = 0;
            for (RecursiveTask<Long> task: subTasks) {
                computedSum += task.join();
            }
            return computedSum;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int half = (startPoint + finishPoint) / 2;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, half);
        RecursiveTask<Long> second = new MyTask(half, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
