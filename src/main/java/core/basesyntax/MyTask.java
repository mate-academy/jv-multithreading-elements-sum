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
        if (distance <= THRESHOLD) {
            long sum = 0;
            for (long i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);

            leftTask.fork();
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }
}
