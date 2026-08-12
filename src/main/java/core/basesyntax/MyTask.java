package core.basesyntax;

import java.util.concurrent.RecursiveTask;

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
        if (startPoint >= finishPoint) {
            return 0L;
        }
        if (finishPoint - startPoint <= THRESHOLD) {
            return sumOfConsecutiveIntegers(startPoint, finishPoint);

        } else {
            int mid = (startPoint + finishPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, mid);
            MyTask rightTask = new MyTask(mid, finishPoint);
            leftTask.fork();
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }

    private long sumOfConsecutiveIntegers(int start, int end) {
        return (long) (end - start) * (start + end - 1) / 2;
    }
}
