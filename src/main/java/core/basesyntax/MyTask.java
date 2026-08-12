package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAGIC_NUMBER = 5;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (startPoint >= finishPoint) {
            return (long) 0;
        }
        if (finishPoint - startPoint > MAGIC_NUMBER * 2) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.join();
            }
        }
        return findSum(startPoint, finishPoint) - finishPoint;
    }

    private Long findSum(int start, int end) {
        int result = 0;
        for (int i = start; i <= end; i++) {
            result += i;
        }
        return (long) result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint + MAGIC_NUMBER,
                finishPoint - MAGIC_NUMBER);
        RecursiveTask<Long> second = new MyTask(startPoint + MAGIC_NUMBER,
                finishPoint - MAGIC_NUMBER);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
