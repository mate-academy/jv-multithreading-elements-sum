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
        if ((finishPoint - startPoint) <= 10) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int mid = (startPoint + finishPoint) / 2;
            MyTask left = new MyTask(startPoint, mid);
            MyTask right = new MyTask(mid, finishPoint);

            left.fork();

            long rightSum = right.compute();
            long leftSum = left.join();
            return rightSum + leftSum;
        }
    }
}
