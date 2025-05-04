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
        if ((finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return sums(startPoint, finishPoint);
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint,
                startPoint + ((finishPoint - startPoint) / 2));
        RecursiveTask<Long> second = new MyTask(startPoint
                + ((finishPoint - startPoint) / 2), finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }

    private long sums(int start, int fin) {
        int current = start;
        long sum = 0L;
        while (current < fin) {
            sum += current;
            current++;
        }
        return sum;
    }
}
