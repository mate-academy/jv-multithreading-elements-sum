package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final long THRESHOLD = 10;
    private long startPoint;
    private long finishPoint;

    public MyTask(long startPoint, long finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long length = finishPoint - startPoint;
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            long midPoint = startPoint + (length / 2);
            MyTask firstSubtask = new MyTask(startPoint, midPoint);
            firstSubtask.fork();
            MyTask secondSubtask = new MyTask(midPoint, finishPoint);
            return secondSubtask.compute() + firstSubtask.join();
        }
    }
}
