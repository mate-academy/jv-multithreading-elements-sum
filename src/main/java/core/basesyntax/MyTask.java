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
        int range = finishPoint - startPoint;

        if (range <= THRESHOLD) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }

        int mid = (startPoint + finishPoint) / 2;

        MyTask leftTask = new MyTask(startPoint, mid);
        MyTask rightTask = new MyTask(mid, finishPoint);

        leftTask.fork();
        rightTask.fork();

        long leftResult = leftTask.join();
        long rightResult = rightTask.join();

        return leftResult + rightResult;
    }
}
