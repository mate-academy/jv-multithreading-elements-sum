package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (getCurrentRange() > 10) {
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
            return (long) IntStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = (getCurrentRange() / 2) + startPoint;
        RecursiveTask<Long> first = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> second = new MyTask(middlePoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }

    private int getCurrentRange() {
        return finishPoint - startPoint;
    }
}
