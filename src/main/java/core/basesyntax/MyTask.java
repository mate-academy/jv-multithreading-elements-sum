package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private final int maxDistance = 10;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= maxDistance) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int mid = startPoint + (finishPoint - startPoint) / 2;
            MyTask task1 = new MyTask(startPoint, mid);
            MyTask task2 = new MyTask(mid, finishPoint);
            task1.fork();
            task2.fork();
            long result1 = task1.join();
            long result2 = task2.join();
            return result1 + result2;
        }
    }
}
