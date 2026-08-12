package core.basesyntax;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int WORKLOAD_THRESHOLD = 10;
    private final int startPoint;
    private final int finishPoint;
    private final int workload;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.workload = finishPoint - startPoint;
    }

    @Override
    protected Long compute() {
        if (workload > WORKLOAD_THRESHOLD) {
            List<RecursiveTask<Long>> recursiveTasks = splitTask();
            recursiveTasks.forEach(ForkJoinTask::fork);

            long result = 0;
            for (RecursiveTask<Long> recursiveTask: recursiveTasks) {
                result += recursiveTask.join();
            }

            return result;
        } else {
            return Long.valueOf(IntStream.range(startPoint, finishPoint).sum());
        }
    }

    private List<RecursiveTask<Long>> splitTask() {
        int middle = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> firstHalf = new MyTask(startPoint, middle);
        RecursiveTask<Long> secondHalf = new MyTask(middle, finishPoint);

        return List.of(firstHalf, secondHalf);
    }
}
