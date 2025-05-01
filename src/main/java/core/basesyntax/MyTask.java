package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int INITIAL_SUBTOTAL = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        Long result = 0L;
        if (finishPoint - startPoint > INITIAL_SUBTOTAL) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTask());
            System.out.println("Splitting task for range from " + startPoint + " to " + finishPoint
                    + " for thread: " + Thread.currentThread().getName());
            for (RecursiveTask<Long> task: subTasks) {
                task.fork();
            }

            for (RecursiveTask<Long> task: subTasks) {
                result += task.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            System.out.println("Doing the task myself for range from " + startPoint + " to "
                    + finishPoint + " for thread: " + Thread.currentThread().getName()
                    + " the sum = " + result);
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        int center = (finishPoint + startPoint) / 2;
        RecursiveTask<Long> firstTask = new MyTask(
                startPoint, center);
        RecursiveTask<Long> secondTask = new MyTask(
                center, finishPoint);
        tasks.add(firstTask);
        tasks.add(secondTask);
        return tasks;
    }
}
