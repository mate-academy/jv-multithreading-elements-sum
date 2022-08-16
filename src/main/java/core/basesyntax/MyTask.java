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
        if (finishPoint - startPoint >= 10) {
            List<RecursiveTask<Long>> tasks = new ArrayList<>(splitTasks());
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> task : tasks) {
                result += task.join();
            }
            return result;
        } else {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> splitTasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        int midPoint = (finishPoint - startPoint) / 2 + startPoint;
        RecursiveTask<Long> task1 = new MyTask(startPoint, midPoint);
        RecursiveTask<Long> task2 = new MyTask(midPoint, finishPoint);
        tasks.add(task1);
        tasks.add(task2);
        return tasks;
    }
}
