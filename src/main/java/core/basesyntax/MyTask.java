package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        Long resultFinish = 0L;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;

        } else {
            resultFinish = IntStream.range(startPoint, finishPoint).mapToLong(n -> (long) n).sum();
        }
        return resultFinish;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int workload = finishPoint - startPoint;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + (workload / 2) + 1);
        RecursiveTask<Long> second = new MyTask(startPoint + (workload / 2) + 1, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
