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
        long distance = finishPoint - startPoint;
        long result = 0;
        // write your code here
        if (distance <= THRESHOLD) {
            for (long i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        } else {
            int mid = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, mid);
            MyTask rightTask = new MyTask(mid, finishPoint);
            leftTask.fork();
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();
            result = leftResult + rightResult;
        }
        return result;
    }
}
