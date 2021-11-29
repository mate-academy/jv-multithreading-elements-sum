package core.basesyntax;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int DISTANCE = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long tmp;
        if (finishPoint - startPoint > DISTANCE) {
            int changePoint = startPoint + DISTANCE;
            RecursiveTask<Long> first = new MyTask(startPoint, changePoint);
            RecursiveTask<Long> second = new MyTask(changePoint, finishPoint);
            List<RecursiveTask<Long>> task = List.of(first, second);
            task.forEach(ForkJoinTask::fork);
            tmp = task.stream().mapToLong(ForkJoinTask::join).sum();
        } else {
            tmp = IntStream.range(startPoint, finishPoint).asLongStream().sum();
        }
        return tmp;
    }
}
