package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    public static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int length = finishPoint - startPoint;
        if (length > THRESHOLD) {
            int middlePoint = startPoint + length / 2;
            MyTask leftTask = new MyTask(startPoint, middlePoint);
            MyTask rightTask = new MyTask(middlePoint, finishPoint);
            leftTask.fork();
            rightTask.fork();
            Long left = leftTask.join();
            Long right = rightTask.join();
        }
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
