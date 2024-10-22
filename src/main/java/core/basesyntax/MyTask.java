package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;

    private int startPoint;

    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= THRESHOLD) {
            return getSum(startPoint, finishPoint);
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);

            left.fork();
            right.fork();

            long rightResult = left.join();
            long leftResult = right.join();
            return Long.sum(leftResult, rightResult);
        }
    }

    private long getSum(long start, long end) {
        if (start == end || start > end) {
            return 0;
        }
        long result = start;
        for (long i = start + 1; i < end; i++) {
            result += i;
        }
        return result;
    }
}
