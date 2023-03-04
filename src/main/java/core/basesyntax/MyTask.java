package core.basesyntax;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;

    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (isNeedToSplitBySubtasks()) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }

        return LongStream.range(startPoint, finishPoint)
                .sum();
    }

    private List<RecursiveTask<Long>> createSubtasks() {
        int middlePoint = getMiddlePoint();

        return List.of(new MyTask(startPoint, middlePoint), new MyTask(middlePoint, finishPoint));
    }

    private boolean isNeedToSplitBySubtasks() {
        return Math.abs(startPoint - finishPoint) > THRESHOLD;
    }

    private int getMiddlePoint() {
        return (startPoint + finishPoint) >> 1;
    }

}
