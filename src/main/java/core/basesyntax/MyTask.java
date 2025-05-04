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
        if (finishPoint - startPoint <= THRESHOLD) {
            return calculateSum();
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);
            leftTask.fork();
            rightTask.fork();
            Long leftResult = leftTask.join();
            Long rightResult = rightTask.join();
            return leftResult + rightResult;
        }
    }

    private Long calculateSum() {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
