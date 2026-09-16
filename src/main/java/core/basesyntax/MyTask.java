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
        int difference = finishPoint - startPoint;

        if (difference <= 0) {
            return 0L;
        }

        if (difference <= 10) {
            long sum = 0;

            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }

            return sum;
        }

        int middle = startPoint + difference / 2;

        MyTask leftTask = new MyTask(startPoint, middle);
        MyTask rightTask = new MyTask(middle, finishPoint);

        leftTask.fork();

        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();

        return leftResult + rightResult;
    }
}
