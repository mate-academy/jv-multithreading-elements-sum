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
        // write your code here
        if (startPoint >= finishPoint) {
            return 0L;
        }

        int range = finishPoint - startPoint;

        if (range <= THRESHOLD) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }

        int middle = startPoint + range / 2;

        MyTask leftTask = new MyTask(startPoint, middle);
        MyTask rightTask = new MyTask(middle, finishPoint);

        leftTask.fork();
        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();

        return leftResult + rightResult;
    }
}
