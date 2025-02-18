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
        if (finishPoint <= startPoint) {
            return 0L;
        }

        int range = finishPoint - startPoint;
        if (range <= THRESHOLD) {
            return calculateSum(startPoint, finishPoint);
        }

        int middle = startPoint + range / 2;

        MyTask leftTask = new MyTask(startPoint, middle);
        MyTask rightTask = new MyTask(middle, finishPoint);

        leftTask.fork();
        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();

        return leftResult + rightResult;
    }

    private long calculateSum(int start, int end) {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += i;
        }
        return sum;
    }
}
