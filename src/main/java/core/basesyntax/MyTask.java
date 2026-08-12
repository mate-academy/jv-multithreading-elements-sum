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
        Long sum = 0L;
        if ((finishPoint - startPoint) < 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        } else {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createTasks());

            for (RecursiveTask<Long> task : recursiveTasks) {
                task.fork();

                sum += task.join();
            }
        }

        return sum;
    }

    private List<RecursiveTask<Long>> createTasks() {
        int middle = startPoint + (finishPoint - startPoint) / 2;
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, middle);
        RecursiveTask<Long> second = new MyTask(middle, finishPoint);
        tasks.add(first);
        tasks.add(second);
        return tasks;
    }
}
