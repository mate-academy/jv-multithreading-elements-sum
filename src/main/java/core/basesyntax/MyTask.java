package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    public static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= THRESHOLD) {
            System.out.printf("Doing sum of number. Thread: %s%n",
                    Thread.currentThread().getName());

            return sum(startPoint, finishPoint);
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;

            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);

            leftTask.fork();
            rightTask.fork();

            long leftResult = leftTask.join();
            long rightResult = rightTask.join();

            return leftResult + rightResult;
        }
    }

    private Long sum(int start, int end) {
        long total = 0;
        for (int i = start; i < end; i++) {
            total += i;
        }
        return total;
    }
}
