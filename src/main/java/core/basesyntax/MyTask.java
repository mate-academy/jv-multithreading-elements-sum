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
        if (finishPoint - startPoint < 10) {
            return findArraySum(startPoint, finishPoint);
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);
            left.fork();
            right.fork();
            return left.join() + right.join();
        }
    }

    private long findArraySum(int startPoint, int finishPoint) {
        long result = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }
}
