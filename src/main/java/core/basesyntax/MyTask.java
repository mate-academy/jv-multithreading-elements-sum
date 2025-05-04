package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            System.out.println("RecursiveTask: splitting the task with the distance : "
                    + distance + "." + Thread.currentThread().getName());
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        }
        System.out.println("RecursiveTask: doing the task myself with the distance : "
                + distance + "." + Thread.currentThread().getName());
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int quarterLength = (finishPoint - startPoint) / 4;
        int firstQuarter = startPoint + quarterLength;
        int secondQuarter = startPoint + 2 * quarterLength;
        int thirdQuarter = startPoint + 3 * quarterLength;
        RecursiveTask<Long> first = new MyTask(startPoint, firstQuarter);
        RecursiveTask<Long> second = new MyTask(firstQuarter, secondQuarter);
        RecursiveTask<Long> third = new MyTask(secondQuarter, thirdQuarter);
        RecursiveTask<Long> fourth = new MyTask(thirdQuarter, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        subTasks.add(third);
        subTasks.add(fourth);
        return subTasks;
    }
}
