package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int TASKS_LIMIT = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int distance = finishPoint - startPoint;
        if (distance <= TASKS_LIMIT) {
            return getSumWithoutRecursion(startPoint, finishPoint);
        }
        List<RecursiveTask<Long>> tasks = createTasks(startPoint, finishPoint, distance);
        for (RecursiveTask<Long> task : tasks) {
            task.fork();
        }
        long result = 0L;
        for (RecursiveTask<Long> task : tasks) {
            result += task.join();
        }
        return result;
    }

    private Long getSumWithoutRecursion(int startPoint, int finishPoint) {
        long result = 0L;
        for (long i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }

    private List<RecursiveTask<Long>> createTasks(int startPoint, int finishPoint, int distance) {
        List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>();
        int finishPointForFirstTask = finishPoint - (int) Math.floor(distance / 2.0);
        int startPointForSecondTask = startPoint + (int) Math.ceil(distance / 2.0);
        recursiveTasks.add(new MyTask(startPoint, finishPointForFirstTask));
        recursiveTasks.add(new MyTask(startPointForSecondTask, finishPoint));
        return recursiveTasks;
    }
}
