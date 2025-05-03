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
        if (finishPoint - startPoint > THRESHOLD) {
            int i = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, i);
            MyTask right = new MyTask(i, finishPoint);

            left.fork();
            right.fork();

            Long join = left.join();
            Long join1 = right.join();

            return join1 + join;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }
}
