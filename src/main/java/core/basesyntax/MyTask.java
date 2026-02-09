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
            for (int i = startPoint; i <= finishPoint; i++) {
                sum += i;
            }
            System.out.println("Task [" + startPoint + ", " + finishPoint + "] = " + sum);
            return sum;
        } else {
            int mid = startPoint + (finishPoint - startPoint) / 2;

            MyTask leftTask = new MyTask(startPoint, mid);
            MyTask rightTask = new MyTask(mid + 1, finishPoint);

            leftTask.fork();
            Long rightResult = rightTask.compute();
            Long leftResult = leftTask.join();

            long total = leftResult + rightResult;
            System.out.println("Merged Task [" + startPoint + ", " + finishPoint + "] = " + total);
            return total;
        }
    }
}
