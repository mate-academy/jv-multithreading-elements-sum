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
        if (finishPoint - startPoint <= THRESHOLD) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; ++i) {
                sum += i;
            }
            return sum;
        } else {
            int midPoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask firstPart = new MyTask(startPoint, midPoint);
            MyTask secondPart = new MyTask(midPoint, finishPoint);
            firstPart.fork();
            Long res2 = secondPart.compute();
            Long res1 = firstPart.join();
            return res1 + res2;
        }
    }
}
