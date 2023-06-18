package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= 10) {
            long sum = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }

            return sum;
        }

        int mid = startPoint + (finishPoint - startPoint) / 2;
        MyTask leftTask = new MyTask(startPoint, mid);
        MyTask rightTask = new MyTask(mid, finishPoint);

        leftTask.fork();
        rightTask.fork();

        Long leftSum = leftTask.join();
        Long rightSum = rightTask.join();

        return leftSum + rightSum;
    }
}
