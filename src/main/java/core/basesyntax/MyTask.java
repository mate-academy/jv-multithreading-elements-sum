package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

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
            return (long) IntStream.range(startPoint, finishPoint)
                .sum();
        }
        int mid = (startPoint + finishPoint) / 2;
        MyTask left = new MyTask(startPoint, mid);
        MyTask right = new MyTask(mid, finishPoint);
        left.fork();
        right.fork();
        Long leftResult = left.join();
        Long rightResult = right.join();
        return leftResult + rightResult;
    }
}
