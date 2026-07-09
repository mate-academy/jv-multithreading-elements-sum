package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    // Встановлюємо поріг, при якому припиняємо поділ (наприклад, 10 елементів)
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
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }

        else {
            int midPoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, midPoint);
            MyTask rightTask = new MyTask(midPoint, finishPoint);

            leftTask.fork();

            Long rightResult = rightTask.compute();
            Long leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }
}
