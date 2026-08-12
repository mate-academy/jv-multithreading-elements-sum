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
            return calculateSum(startPoint, finishPoint);
        } else {
            int middlePoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middlePoint);
            MyTask right = new MyTask(middlePoint, finishPoint);

            left.fork();
            right.fork();

            Long leftResult = left.join();
            Long rightResult = right.join();

            return leftResult + rightResult;
        }
    }

    private Long calculateSum(int startPoint, int finishPoint) {
        long sum = 0L;

        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }

        return sum;
    }
}
