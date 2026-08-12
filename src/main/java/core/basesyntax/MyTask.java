package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

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
        if ((finishPoint - startPoint) <= THRESHOLD) {
            return (long) IntStream.range(startPoint, finishPoint).sum();
        }

        int mid = (startPoint + finishPoint) / 2;
        MyTask left = new MyTask(startPoint, mid);
        MyTask right = new MyTask(mid, finishPoint);

        left.fork();
        Long rightResult = right.compute();
        Long leftResult = left.join();

        return leftResult + rightResult;
    }
}
