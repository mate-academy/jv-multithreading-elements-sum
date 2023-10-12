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
        int workload = finishPoint - startPoint;
        int result = 0;
        if (workload > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }

            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return (long) result;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return (long) result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        int middle = (startPoint + finishPoint) / 2;
        RecursiveTask<Long> task1 = new MyTask(startPoint, middle);
        RecursiveTask<Long> task2 = new MyTask(middle, finishPoint);
        tasks.add(task1);
        tasks.add(task2);
        return tasks;
    }
}
