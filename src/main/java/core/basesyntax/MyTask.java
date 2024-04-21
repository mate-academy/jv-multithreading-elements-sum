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
        if (finishPoint - startPoint > THRESHOLD) {
            int midPoint = startPoint + (finishPoint - startPoint) / 2;
            var left = new MyTask(startPoint, midPoint);
            var right = new MyTask(midPoint, finishPoint);

            left.fork();
            right.fork();

            long leftResult = left.join();
            long rightResult = right.join();

            return leftResult + rightResult;
        } else {
            var result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return (long) result;
        }
    }
}
