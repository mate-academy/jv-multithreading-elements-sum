package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

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
            return LongStream.range(startPoint, finishPoint)
                    .sum();
        }

        int middle = startPoint + (finishPoint - startPoint) / 2;

        MyTask left = new MyTask(startPoint, middle);
        MyTask right = new MyTask(middle, finishPoint);

        left.fork();

        long rightResult = right.compute();
        long leftResult = left.join();

        return leftResult + rightResult;
    }
}
