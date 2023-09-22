package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

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
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            subTasks.forEach(ForkJoinTask::fork);

            return subTasks.stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        } else {
            return LongStream.range(startPoint, finishPoint)
                    .sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int mid = finishPoint + startPoint;
        RecursiveTask<Long> first = new MyTask(startPoint, mid / 2);
        RecursiveTask<Long> second = new MyTask(mid / 2, finishPoint);
        return List.of(first, second);
    }
}
