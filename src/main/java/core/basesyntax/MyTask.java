package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {

        if (finishPoint - startPoint <= 10) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }

        int middlePoint = (startPoint + finishPoint) / 2;

        MyTask leftTask = new MyTask(startPoint, middlePoint);
        MyTask rightTask = new MyTask(middlePoint, finishPoint);

        leftTask.fork();

        long rightResult = rightTask.compute();

        long leftResult = leftTask.join();

        return rightResult + leftResult;
    }
}
