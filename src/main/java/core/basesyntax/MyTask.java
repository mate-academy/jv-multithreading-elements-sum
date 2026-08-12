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
        if (finishPoint - startPoint <= THRESHOLD) {
            return findSum(startPoint, finishPoint);
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftSum = new MyTask(startPoint, middle);
            MyTask rightSum = new MyTask(middle, finishPoint);

            leftSum.fork();
            rightSum.fork();
            int rightResult = Math.toIntExact(rightSum.join());
            int leftResult = Math.toIntExact(leftSum.join());

            return (long) Math.addExact(rightResult, leftResult);
        }
    }

    private long findSum(int startPoint, int finishPoint) {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
