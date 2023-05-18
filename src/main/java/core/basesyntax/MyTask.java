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
        int length = finishPoint - startPoint;
        if (length <= THRESHOLD) {
            return computeDirectly();
        }

        int midpoint = startPoint + length / 2;
        MyTask leftTask = new MyTask(startPoint, midpoint);
        MyTask rightTask = new MyTask(midpoint, finishPoint);

        leftTask.fork();
        rightTask.fork();

        Long leftResult = leftTask.join();
        Long rightResult = rightTask.join();

        return leftResult + rightResult;
    }

    private Long computeDirectly() {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
