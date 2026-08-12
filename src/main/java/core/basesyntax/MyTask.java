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
        if ((finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> tasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            Long sum = 0L;
            for (RecursiveTask<Long> task : tasks) {
                sum += task.join();
            }
            return sum;
        } else {
            Long sum = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        RecursiveTask<Long> firstTask = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> secondTask = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        tasks.add(firstTask);
        tasks.add(secondTask);
        return tasks;
    }
}
