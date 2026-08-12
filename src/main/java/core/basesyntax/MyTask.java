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
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long sum = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                sum += subTask.join();
            }
            return sum;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middle = finishPoint - (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, middle);
        RecursiveTask<Long> second = new MyTask(middle, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
