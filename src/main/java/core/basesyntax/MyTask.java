package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;
    private int availablePoints;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        availablePoints = finishPoint - startPoint;
    }

    @Override
    protected Long compute() {
        if (availablePoints > THRESHOLD) {
            List<RecursiveTask> subTasks = createSubTasks();
            Long result = 0L;
            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
                result += task.join();
            }
            return result;
        }
        return LongStream.range(startPoint, finishPoint).sum();
    }

    private List<RecursiveTask> createSubTasks() {
        int pointInTheMiddle = startPoint + availablePoints / 2;
        RecursiveTask<Long> left = new MyTask(startPoint, pointInTheMiddle);
        RecursiveTask<Long> right = new MyTask(pointInTheMiddle, finishPoint);
        return List.of(left, right);
    }
}
