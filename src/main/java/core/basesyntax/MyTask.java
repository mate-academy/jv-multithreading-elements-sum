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
        int distance = finishPoint - startPoint;
        long sum = 0;
        if (distance <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
        int middle = startPoint + distance / 2;
        MyTask leftTask = new MyTask(startPoint, middle);
        MyTask rightTask = new MyTask(middle, finishPoint);

        leftTask.fork();
        rightTask.fork();
        long leftResult = leftTask.join();
        long rightResult = rightTask.join();
        return leftResult + rightResult;
    }
}
