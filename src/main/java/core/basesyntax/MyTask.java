package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
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
        if (finishPoint - startPoint <= THRESHOLD) {
            long sum = LongStream.range(startPoint, finishPoint).sum();
            System.out.println("startPoint: " + startPoint
                    + " finishPoint: " + finishPoint
                    + " sum: " + sum);
            return sum;
        }
        List<RecursiveTask<Long>> subTasks = createSubTasks();
        for (RecursiveTask<Long> subTask : subTasks) {
            subTask.fork();
        }
        long sum = 0;
        for (RecursiveTask<Long> subTask : subTasks) {
            sum += subTask.join();
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        subTasks.add(new MyTask(startPoint, middlePoint));
        subTasks.add(new MyTask(middlePoint, finishPoint));
        return subTasks;
    }
}
