package core.basesyntax;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAX_DIFFERENCE = 10;

    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > MAX_DIFFERENCE) {
            return ForkJoinTask.invokeAll(split()).stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }
        return process(startPoint, finishPoint);
    }

    private Collection<MyTask> split() {
        return List.of(
                new MyTask(startPoint, (startPoint + finishPoint) / 2),
                new MyTask((startPoint + finishPoint) / 2, finishPoint)
        );
    }

    private Long process(int startPoint, int finishPoint) {
        return LongStream.range(startPoint, finishPoint).sum();
    }
}
