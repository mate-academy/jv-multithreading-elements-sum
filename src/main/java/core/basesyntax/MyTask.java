package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final static int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        if (finishPoint - startPoint <= THRESHOLD) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int middle = (startPoint + finishPoint) / 2;

            RecursiveTask<Long> left = new MyTask(startPoint, middle);
            RecursiveTask<Long> right = new MyTask(middle, finishPoint);

            left.fork();
            right.fork();

            return left.join() + right.join();
        }
    }
}
