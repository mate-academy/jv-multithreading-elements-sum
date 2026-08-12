package core.basesyntax;

import java.util.List;
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
        long sum = 0;
        if (Math.abs(finishPoint) - Math.abs(startPoint) > 10) {
            List<RecursiveTask<Long>> subTasks = createSubTask();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
                sum += subTask.join();
            }
            return sum;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        int finishSubPoint = startPoint + (finishPoint - startPoint) / 2;
        return List.of(new MyTask(startPoint, finishSubPoint),
                new MyTask(finishSubPoint, finishPoint));
    }
}
