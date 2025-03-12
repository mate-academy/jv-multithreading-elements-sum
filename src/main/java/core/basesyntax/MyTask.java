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
            return getElementSum(startPoint, finishPoint);
        }
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        MyTask leftTask = new MyTask(startPoint, middlePoint);
        MyTask rightTask = new MyTask(middlePoint, finishPoint);

        rightTask.fork();
        return leftTask.compute() + rightTask.join();
    }

    private Long getElementSum(int startPoint, int finishPoint) {
        int sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return (long) sum;
    }
}
