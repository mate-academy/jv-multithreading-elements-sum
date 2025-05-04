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
            return findSum(startPoint, finishPoint);
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);
            left.fork();
            right.fork();
            Long leftSum = left.join();
            Long rightSum = right.join();
            return leftSum + rightSum;

        }
        // write your code here
    }

    private Long findSum(int startPoint, int finishPoint) {
        int sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return (long) sum;
    }
}
