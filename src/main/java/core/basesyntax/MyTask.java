package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAX_DISTANCE_FOR_ONE_THREAD = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (finishPoint - startPoint > MAX_DISTANCE_FOR_ONE_THREAD) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                sum += subTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTask = new ArrayList<>();
        int finishForFirstThreadStartForSecond = startPoint + MAX_DISTANCE_FOR_ONE_THREAD;
        RecursiveTask<Long> first = new MyTask(startPoint, finishForFirstThreadStartForSecond);
        RecursiveTask<Long> second = new MyTask(finishForFirstThreadStartForSecond, finishPoint);
        subTask.add(first);
        subTask.add(second);
        return subTask;
    }
}
