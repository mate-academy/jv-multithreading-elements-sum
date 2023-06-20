package core.basesyntax;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final long START_AND_FINISH_POINTS_DISTANCE = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (Math.abs(finishPoint) - Math.abs(startPoint) > START_AND_FINISH_POINTS_DISTANCE) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            long result = 0;
            subTasks.forEach(ForkJoinTask::fork);
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int midpoint = startPoint + (finishPoint - startPoint) / 2;
        return List.of(new MyTask(startPoint, midpoint),
                new MyTask(midpoint, finishPoint));
    }
}
