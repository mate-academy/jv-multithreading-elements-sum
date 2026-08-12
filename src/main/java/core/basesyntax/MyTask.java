package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

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
        if ((finishPoint - startPoint - 1) > THRESHOLD) {
            int center = (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, startPoint + center);
            MyTask right = new MyTask(startPoint + center, finishPoint);

            left.fork();
            return left.join() + right.compute();
        }

        return LongStream.range(startPoint, finishPoint).sum();
    }
}
