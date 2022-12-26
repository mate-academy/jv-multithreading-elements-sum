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
        if (finishPoint - startPoint > 10) {
            System.out.println("RecursiveTask: Splitting the task, "
                    + " start point " + startPoint + ", finish point - "
                    + finishPoint + ". " + Thread.currentThread().getName());
            List<RecursiveTask<Long>> subtasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subtask: subtasks) {
                subtask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask: subtasks) {
                result += subTask.join();
            }

            return result;

        } else {
            System.out.println("RecursiveTask: Doing the task myself, "
                    + " start point " + startPoint + ", finish point - "
                            + finishPoint + ". " + Thread.currentThread().getName());
            long sum = 0;
            for (long i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            System.out.println("---------- sum - " + sum);
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (finishPoint + startPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
