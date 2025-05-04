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
        long sum = 0;
        if (finishPoint - startPoint <= THRESHOLD) {
            for (long i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            System.out.println("Sum is " + sum);
        } else {
            long mid = startPoint + (finishPoint - startPoint) / 2;
            MyTask first = new MyTask(startPoint, (int) mid);
            MyTask second = new MyTask((int) mid, finishPoint);
            first.fork();
            second.fork();
            sum = (first.join() + second.join());
        }
        return sum;
    }
}
