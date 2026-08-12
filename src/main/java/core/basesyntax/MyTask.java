package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private int threshold;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.threshold = finishPoint - startPoint;
    }

    @Override
    protected Long compute() {
        if (threshold <= 10) {
            System.out.printf("RecursiveTask: Doing the task where start = %s, and end = %s. "
                    + "Thread: %s%n", startPoint, finishPoint, Thread.currentThread().getName());
            return sumOfConsecutiveIntegers(startPoint, finishPoint);
        } else {
            System.out.printf("RecursiveTask: Splitting the task where start = %s, and end = %s. "
                    + "Thread: %s%n", startPoint, finishPoint, Thread.currentThread().getName());
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

    private Long sumOfConsecutiveIntegers(int startPoint, int finishPoint) {
        // Ensure the finishPoint is greater than startPoint
        if (finishPoint <= startPoint) {
            return 0L; // No integers to sum in this case
        }

        // First integer to include in the sum
        int first = startPoint;
        // Last integer to include in the sum
        int last = finishPoint - 1;

        // Number of integers in the range
        int count = last - first + 1;

        // Sum of the arithmetic series
        // Sum = count / 2 * (first + last)
        Long sum = Long.valueOf(count * (first + last) / 2);

        return sum;
    }
}
