package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final long THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        if (finishPoint - startPoint <= THRESHOLD) {
            return calculateSum(startPoint, finishPoint);
        }
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;

        MyTask leftTask = new MyTask(startPoint, middlePoint);
        MyTask rightTask = new MyTask(middlePoint, finishPoint);
        leftTask.fork();
        rightTask.fork();

        long leftResult = leftTask.join();
        long rightResult = rightTask.join();

        return leftResult + rightResult;
    }

    private long calculateSum(int start, int finish) {
        long sum = 0;
        for (int i = start; i < finish; i++) {
            sum += i;
        }
        return sum;
    }
}
