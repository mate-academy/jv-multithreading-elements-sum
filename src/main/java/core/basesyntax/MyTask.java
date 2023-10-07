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
        // write your code here
        if (getCurrentDistance() > 10) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return getConsecutiveSum(startPoint, finishPoint);
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> firstTask = new MyTask(
                startPoint, (int) (startPoint + getCurrentDistance() / 2));
        RecursiveTask<Long> secondTask = new MyTask(
                (int) (startPoint + getCurrentDistance() / 2), finishPoint);
        subTasks.add(firstTask);
        subTasks.add(secondTask);
        return subTasks;
    }

    private Long getCurrentDistance() {
        return (long) (finishPoint - startPoint);
    }

    private long getConsecutiveSum(long from, long to) {
        long sum = 0;
        for (long i = from; i < to; i++) {
            sum += i;
        }
        return sum;
    }
}
