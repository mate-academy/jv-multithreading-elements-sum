package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private final int threshold = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint < threshold) {
            return findSum(startPoint, finishPoint);
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);

            leftTask.fork();

            Long right = rightTask.compute();
            Long left = leftTask.join();

            return left + right;
        }
    }

    private long findSum(int startPoint, int finishPoint) {
        return LongStream.range(startPoint, finishPoint)
                .sum();
    }
}
