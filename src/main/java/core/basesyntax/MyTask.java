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
        int range = finishPoint - startPoint;
        if (range <= THRESHOLD) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
        int midPoint = startPoint + range / 2;

        MyTask leftTask = new MyTask(startPoint, midPoint);
        MyTask rightTask = new MyTask(midPoint, finishPoint);

        leftTask.fork();
        rightTask.fork();

        Long rightResult = rightTask.join();
        Long leftResult = leftTask.join();

        return rightResult + leftResult;
    }
}
