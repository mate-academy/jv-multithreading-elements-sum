package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    public static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= THRESHOLD) {
            long sum = 0;

            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }

            return sum;
        } else {
            int middlePoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask first = new MyTask(startPoint, middlePoint);
            MyTask second = new MyTask(middlePoint, finishPoint);

            first.fork();
            second.fork();
            long firstResult = first.join();
            long secondResult = second.join();

            return firstResult + secondResult;
        }
    }
}
