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
        long sum = 0;
        if (finishPoint - startPoint <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);
            left.fork();
            right.fork();
            sum = left.join() + right.join();
        }
        return sum;
    }
}
