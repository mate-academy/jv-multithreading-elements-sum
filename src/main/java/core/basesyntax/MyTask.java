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
            System.out.println(Thread.currentThread().getName() + " : " + sum);
            return sum;
        } else {
            int median = (startPoint + finishPoint) / 2;
            MyTask leftTast = new MyTask(startPoint, median);
            MyTask rightTast = new MyTask(median, finishPoint);
            Long leftResult = leftTast.fork().join();
            Long rightResult = rightTast.fork().join();
            System.out.println(Thread.currentThread().getName() + " : "
                    + (leftResult + rightResult));
            return leftResult + rightResult;
        }
    }
}
