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
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createSubTasks());

            List<Long> results = new ArrayList<>();
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                recursiveTask.fork();
                results.add(recursiveTask.join());
            }
            return results.stream()
                    .mapToLong(Long::longValue)
                    .sum();
        } else {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int middle = (startPoint + finishPoint) / 2;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, middle);
        RecursiveTask<Long> second = new MyTask(middle, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
