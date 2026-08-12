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
        if (startPoint >= finishPoint) {
            return 0L;
        }
        int dif = finishPoint - startPoint;
        if (dif > 10) {
            int mid = startPoint + dif / 2;

            MyTask leftTask = new MyTask(startPoint, mid);
            MyTask rightTask = new MyTask(mid, finishPoint);

            leftTask.fork();
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();

            return leftResult + rightResult;
        } else {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
