package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
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
        long distance = finishPoint - startPoint;
        if (distance > 10) {
            List<RecursiveTask<Long>> list = new ArrayList<>(createSubTask());
            list.forEach(ForkJoinTask::fork);
            long res = 0;
            for (RecursiveTask<Long> task : list) {
                res += task.join();
            }
            return res;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> list = new ArrayList<>();
        RecursiveTask<Long> first =
                new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second =
                new MyTask((startPoint + finishPoint) / 2, finishPoint);
        list.add(first);
        list.add(second);
        return list;
    }
}
