package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks(distance / 2));
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return calculateSum(startPoint, finishPoint);
        }
    }

    private List<RecursiveTask<Long>> createSubTasks(int delta) {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int intermediatePoint = startPoint + delta;
        RecursiveTask<Long> first = new MyTask(startPoint, intermediatePoint);
        RecursiveTask<Long> second = new MyTask(intermediatePoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }

    private long calculateSum(int start, int stop) {
        long result = 0L;
        while (start < stop) {
            result += start;
            start++;
        }
        return result;
    }
}
