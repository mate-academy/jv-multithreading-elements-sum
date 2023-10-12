package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;
    private final int currentRange;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.currentRange = finishPoint - startPoint;
    }

    @Override
    protected Long compute() {
        if (currentRange > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : recursiveTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : recursiveTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return IntStream.range(startPoint, finishPoint)
                    .asLongStream()
                    .sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = (currentRange / 2) + startPoint;
        RecursiveTask<Long> first = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> second = new MyTask(middlePoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
