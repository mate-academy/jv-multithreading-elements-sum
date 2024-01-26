package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;
    private int workload;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long sum = 0L;
        if (finishPoint - startPoint > THRESHOLD) {
            int middlePoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middlePoint);
            MyTask right = new MyTask(middlePoint, finishPoint);

            left.fork();
            right.fork();

            Long leftResult = left.join();
            Long rightResult = right.join();

            return leftResult + rightResult;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        }
        return sum;
    }
}
