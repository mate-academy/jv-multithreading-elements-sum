package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final long THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (finishPoint - startPoint <= THRESHOLD) {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        } else {
            List<RecursiveTask<Long>> tasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task : tasks) {
                result += task.join();
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> left = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> right = new MyTask(middlePoint, finishPoint);
        subTasks.add(left);
        subTasks.add(right);
        return subTasks;
    }
}
