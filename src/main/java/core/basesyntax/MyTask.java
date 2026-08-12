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
        int length = finishPoint - startPoint;
        if (length <= THRESHOLD) {
            return computeDirectly();
        }
        MyTask leftTask = new MyTask(startPoint, startPoint + length / 2);
        MyTask rightTask = new MyTask(startPoint + length / 2, finishPoint);

        leftTask.fork();
        rightTask.fork();

        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();

        return leftResult + rightResult;
    }

    private Long computeDirectly() {
        long sum = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
