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
        if ((finishPoint - startPoint) <= 10) {
            System.out.printf("RecursiveTask: Doing the task where start = %s, "
                    + "and finish = %s. Thread: %s%n", startPoint, finishPoint,
                    Thread.currentThread().getName());
            return sum(startPoint, finishPoint);
        } else {
            System.out.printf("RecursiveTask: Splitting the task where start = %s, "
                    + "and finish = %s. Thread: %s%n", startPoint, finishPoint,
                    Thread.currentThread().getName());
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);

            leftTask.fork();
            rightTask.fork();
            long rightResult = rightTask.join();
            long leftResult = leftTask.join();

            return rightResult + leftResult;
        }
    }

    private long sum(int startPoint, int finishPoint) {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
