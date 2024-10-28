package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;

    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint >= THRESHOLD) {
            int mid = (startPoint + finishPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, mid);
            MyTask rightTask = new MyTask(mid, finishPoint);
            leftTask.fork();
            Long leftValue = leftTask.join();
            Long rightValue = rightTask.compute();
            return leftValue + rightValue;
        } else {
            return LongStream.range(startPoint, finishPoint)
                    .reduce(Long::sum)
                    .orElse(0);
        }
    }
}
