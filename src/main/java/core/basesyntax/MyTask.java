package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private long startPoint;
    private long finishPoint;

    public MyTask(long startPoint, long finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long distance = finishPoint - startPoint;
        if (distance <= THRESHOLD) {
            return calculateSum();
        } else {
            List<MyTask> subTasks = createSubTasks();
            subTasks.forEach(MyTask::fork);
            return subTasks.stream()
                    .mapToLong(MyTask::join)
                    .sum();
        }
    }

    private List<MyTask> createSubTasks() {
        long middle = (startPoint + finishPoint) / 2;
        MyTask left = new MyTask(startPoint, middle);
        MyTask right = new MyTask(middle, finishPoint);
        return List.of(left, right);
    }

    private long calculateSum() {
        long sum = 0;
        for (long i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        System.out.println("Computed sum from " + startPoint + " to " + finishPoint + ": " + sum);
        return sum;
    }
}
