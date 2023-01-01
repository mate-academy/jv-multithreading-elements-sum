package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        Long sum = 0L;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> recursiveTasks = createSubTasks();
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                recursiveTask.fork();
                sum += recursiveTask.join();
            }
            return sum;
        }
        return LongStream.range(startPoint, finishPoint).sum();
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int nextPoint = startPoint + 9;
        RecursiveTask<Long> first = new MyTask(startPoint, nextPoint);
        RecursiveTask<Long> second = new MyTask(nextPoint, finishPoint);
        return List.of(first, second);
    }
}
