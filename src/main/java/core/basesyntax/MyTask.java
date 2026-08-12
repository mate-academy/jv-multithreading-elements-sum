package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= THRESHOLD) {
            System.out.printf(("Recursive task: doing the task where start point= %s "
                            + "and finish point = %s. Thread name = %s%n"),
                    startPoint, finishPoint, Thread.currentThread().getName());
            long result = findSum(startPoint, finishPoint);
            System.out.printf("Result of sum where start point: %s and finish point: %s, "
                            + "thread name: %s = %s%n",
                    startPoint, finishPoint, Thread.currentThread().getName(), result);
            return result;
        } else {
            System.out.printf(("Recursive task: splitting the task where start point= %s "
                            + "and finish point = %s. Thread name = %s%n"),
                    startPoint, finishPoint, Thread.currentThread().getName());

            int middlePoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middlePoint);
            MyTask rightTask = new MyTask(middlePoint, finishPoint);

            leftTask.fork();
            long leftResult = leftTask.join();
            Long rightResult = rightTask.compute();

            long result = leftResult + rightResult;
            System.out.printf("Result of splitting where start point: %s and finish point: %s, "
                            + "thread name: %s = %s%n",
                    startPoint, finishPoint, Thread.currentThread().getName(), result);
            return result;
        }
    }

    private long findSum(int start, int end) {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += i;
        }
        return sum;
    }
}
