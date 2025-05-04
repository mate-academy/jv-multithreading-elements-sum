package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint >= THRESHOLD) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long sum = 0L;
            for (RecursiveTask<Long> subTask : subTasks) {
                sum += subTask.join();
            }
            return sum;
        } else {
            long sum = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<RecursiveTask<Long>>();
        RecursiveTask<Long> left = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> right = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        subTasks.add(left);
        subTasks.add(right);
        return subTasks;
    }
}
