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
            long sum = 0L;
            while (startPoint < finishPoint) {
                sum += startPoint++;
            }
            return sum;
        }

        int center = startPoint + (finishPoint - startPoint) / 2;
        MyTask leftTask = new MyTask(startPoint, center);
        MyTask rightTask = new MyTask(center, finishPoint);

        leftTask.fork();
        rightTask.fork();

        return leftTask.join() + rightTask.join();
    }
}
