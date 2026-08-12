package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final long THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;
    
    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long range = finishPoint - startPoint;
        if (range <= THRESHOLD) {
            long sum = 0;
            for (long i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = startPoint + (range / 2);
            MyTask leftTask = new MyTask(startPoint, (int) mid);
            MyTask rightTask = new MyTask((int) mid, finishPoint);
            invokeAll(leftTask, rightTask);
            return leftTask.join() + rightTask.join();
        }
    }
}
