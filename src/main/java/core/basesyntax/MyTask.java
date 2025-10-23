package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {

    private final long workload = 10L;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= workload) {
            System.out.printf("Doing task where start = %s, and end = %s. Thread: %s%n",
                    startPoint, finishPoint, Thread.currentThread().getName());
            int result = 0;
            for (int i = startPoint; i <= finishPoint - 1; i++) {
                result += i;
            }
            System.out.printf("Result: %d. Thread: %s%n", result, Thread.currentThread().getName());
            return (long) result;
        } else {
            System.out.printf("Splitting task where start = %s, and end = %s. Thread: %s%n",
                    startPoint, finishPoint, Thread.currentThread().getName());
            int middle = (finishPoint + startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);

            left.fork();

            Long rightResult = right.compute();
            Long leftResult = left.join();

            Long result = leftResult + rightResult;
            System.out.printf("Result: %d. Thread: %s%n", result, Thread.currentThread().getName());
            return result;
        }
    }
}
