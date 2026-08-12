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
        if (finishPoint - startPoint <= 10) {
            return sum(startPoint, finishPoint);
        } else {
            int middlePoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middlePoint);
            MyTask rightTask = new MyTask(middlePoint, finishPoint);

            leftTask.fork();
            rightTask.fork();

            Long leftResult = leftTask.join();
            Long rightResult = rightTask.join();
            return Long.sum(leftResult, rightResult);
        }
    }

    private Long sum(int startPoint, int finishPoint) {
        int sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return (long) sum;
    }
}
