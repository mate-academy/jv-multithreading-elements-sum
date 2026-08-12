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
        if (Math.abs(finishPoint - startPoint) <= THRESHOLD) {
            System.out.printf("Recursive task in thread %s work on summa for range %d - %d%n",
                    Thread.currentThread().getName(), startPoint, startPoint + THRESHOLD);
            return sumAll(startPoint, finishPoint);
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);

            leftTask.fork();
            Long rightVal = rightTask.compute();
            Long leftVal = leftTask.join();

            return rightVal + leftVal;
        }
    }

    private Long sumAll(int startPoint, int finishPoint) {
        Long sum = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
