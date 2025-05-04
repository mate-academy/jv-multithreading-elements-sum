package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long numberStringLength = finishPoint - (long) startPoint;
        if (numberStringLength > 10) {
            System.out.println("RecursiveTask: splitting the task with workload : "
                    + numberStringLength + ". " + Thread.currentThread().getName());
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
                System.out.println("RecursiveTask: Doing the task myself with workload: "
                        + numberStringLength + ". " + Thread.currentThread().getName());
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint
                + (finishPoint - startPoint) / 2);
        RecursiveTask<Long> second = new MyTask(startPoint
                + (finishPoint - startPoint) / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
