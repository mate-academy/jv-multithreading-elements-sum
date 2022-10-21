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
        int difference = finishPoint - startPoint;
        if (difference > 10) {
            System.out.println("RecursiveTask: Splitting task, difference : "
                    + difference + ". " + Thread.currentThread().getName());
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubtasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            System.out.println("RecursiveAction: Doing task myself, difference : "
                    + difference + ". " + Thread.currentThread().getName());
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubtasks() {
        int difference = (finishPoint - startPoint) / 2;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        MyTask first = new MyTask(startPoint, startPoint + difference);
        MyTask second = new MyTask(startPoint + difference, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
