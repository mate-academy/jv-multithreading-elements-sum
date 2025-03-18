package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private int threshold = 2;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {

        if (finishPoint - startPoint <= threshold) {
            System.out.printf("Calculating sum from %s to %s. Thread: %s%n",
                    startPoint, finishPoint, Thread.currentThread().getName());

            return (long) calculateSum(startPoint, finishPoint);
        } else {
            System.out.printf("Splitting the task with start: %s, finish: %s. Thread: %s%n",
                    startPoint, finishPoint, Thread.currentThread().getName());

            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middle);
            MyTask rightTask = new MyTask(middle, finishPoint);

            leftTask.fork();
            rightTask.fork();

            Long rightResult = rightTask.join();
            Long leftResult = leftTask.join();
            return rightResult + leftResult;
        }
    }

    private int calculateSum(int start, int finish) {
        int sum = 0;
        for (int i = start; i < finish; i++) {
            sum += i;
        }
        return sum;
    }
}
