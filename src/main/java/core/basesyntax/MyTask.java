package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if ((finishPoint - startPoint) > 10) {
            int mid = startPoint + (finishPoint - startPoint) / 2;
            RecursiveTask<Long> left = new MyTask(startPoint, mid);
            RecursiveTask<Long> right = new MyTask(mid, finishPoint);
            left.fork();
            right.fork();
            return left.join() + right.join();
        } else if ((finishPoint - startPoint) <= 0) {
            return 0L;
        } else {
            int result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return Long.valueOf(result);
        }
    }
}
