package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final int startPoint;
    private final int finishPoint;
    private long sum;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= THRESHOLD) {
            return sum(startPoint, finishPoint);
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);
            leftTask.fork();
            rightTask.fork();
            long rightResult = rightTask.join();
            long leftResult = leftTask.join();
            return rightResult + leftResult;
        }
    }

    private long sum(int startPoint, int finishPoint) {
        int count = startPoint;
        while (count < finishPoint) {
            sum += count++;
        }
        return sum;
    }
}
