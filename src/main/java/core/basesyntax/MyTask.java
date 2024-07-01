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
            return sumRange();
        } else {
            int midPoint = (startPoint + finishPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, midPoint);
            MyTask rightTask = new MyTask(midPoint, finishPoint);

            leftTask.fork(); // asynchronously execute the left task
            long rightResult = rightTask.compute(); // compute the right task directly
            long leftResult = leftTask.join(); // wait for the left task to
            // complete and get its result

            return leftResult + rightResult;
        }
    }

    private long sumRange() {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
