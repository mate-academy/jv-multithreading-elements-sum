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
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            List<RecursiveTask<Long>> tasks = createTasks();
            tasks.forEach(RecursiveTask<Long>::fork);
            return tasks.stream()
                    .mapToLong(RecursiveTask<Long>::join)
                    .sum();
        }
        return LongStream.range(startPoint, finishPoint).sum();
    }

    private List<RecursiveTask<Long>> createTasks() {
        int middle = (finishPoint + startPoint) >> 1;
        RecursiveTask<Long> first = new MyTask(startPoint, middle);
        RecursiveTask<Long> second = new MyTask(middle, finishPoint);
        return List.of(first, second);
    }
}
