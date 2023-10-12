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
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            System.out.println("Calculating sum from " + startPoint
                    + " to " + finishPoint + ": " + sum);
            return sum;
        } else {
            int mid = (startPoint + finishPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, mid);
            MyTask rightTask = new MyTask(mid, finishPoint);

            leftTask.fork();
            rightTask.fork();

            Long leftResult = leftTask.join();
            Long rightResult = rightTask.join();

            System.out.println("Merging results from " + startPoint + " to " + mid
                    + " and from " + mid + " to " + finishPoint);
            return leftResult + rightResult;
        }
    }
}
