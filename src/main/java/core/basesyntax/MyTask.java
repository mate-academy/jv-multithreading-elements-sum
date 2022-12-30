package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (finishPoint - startPoint < THRESHOLD) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        } else {
            List<RecursiveTask<Long>> subTasks = createSubTask();
            for (RecursiveTask<Long> subtask : subTasks) {
                subtask.fork();
            }
            for (RecursiveTask<Long> subtask : subTasks) {
                sum += subtask.join();
            }
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        int middle = (startPoint + finishPoint) / 2;
        MyTask task1 = new MyTask(startPoint, middle);
        MyTask task2 = new MyTask(middle, finishPoint);
        tasks.add(task1);
        tasks.add(task2);
        return tasks;
    }
}
