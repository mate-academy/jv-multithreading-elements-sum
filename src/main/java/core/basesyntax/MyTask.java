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
        if ((finishPoint - startPoint) <= THRESHOLD) {
            return getSum(startPoint, finishPoint);
        }
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        MyTask leftTask = new MyTask(startPoint, middlePoint);
        MyTask rightTask = new MyTask(middlePoint, finishPoint);
        leftTask.fork();
        rightTask.fork();
        return leftTask.join() + rightTask.join();
    }

    private long getSum(int startPoint, int finishPoint) {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
