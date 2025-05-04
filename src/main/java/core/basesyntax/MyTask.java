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
        if (finishPoint - startPoint <= THRESHOLD) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum = sum + i;
            }
        } else {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                sum += subTask.join();
            }
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int mid = (startPoint + finishPoint) / 2;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, mid);
        RecursiveTask<Long> second = new MyTask(mid, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
