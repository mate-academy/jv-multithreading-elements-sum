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
        if (Math.abs(finishPoint - startPoint) <= 10) {
            long sum = 0;
            for (long i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, (int) middle);
            MyTask rightTask = new MyTask((int) middle, finishPoint);
            leftTask.fork();
            rightTask.fork();
            long leftResult = leftTask.join();
            long rightResult = rightTask.join();
            return leftResult + rightResult;
        }
    }
}
