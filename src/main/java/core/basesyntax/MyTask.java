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
        // write your code here
        long sum = 0;
        if (finishPoint - startPoint > 10) {
            System.out.println("Splitting " + startPoint + " - " + finishPoint + " "
                    + Thread.currentThread().getName());

            List<RecursiveTask<Long>> tasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task : tasks) {
                sum += task.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        tasks.add(new MyTask(startPoint, (startPoint + finishPoint) / 2));
        tasks.add(new MyTask((startPoint + finishPoint) / 2, finishPoint));
        return tasks;
    }
}
