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
        int workLoad = finishPoint - startPoint;
        long result = 0;
        if (workLoad > 10) {
            System.out.println("RecursiveTask: Splitting the task with workload: "
                    + workLoad + ". " + Thread.currentThread().getName());
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            System.out.println("RecursiveTask: Doing the task myself with workload: "
                    + workLoad + ". " + Thread.currentThread().getName());
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        int workLoad = (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + workLoad);
        RecursiveTask<Long> second = new MyTask(startPoint + workLoad, finishPoint);
        tasks.add(first);
        tasks.add(second);
        return tasks;
    }
}
