package core.basesyntax;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int LIMIT = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        Long result;
        int workLout = finishPoint - startPoint;
        if (workLout >= LIMIT) {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            RecursiveTask<Long> first = new MyTask(startPoint, middle);
            RecursiveTask<Long> second = new MyTask(middle, finishPoint);
            first.fork();
            second.fork();
            Long firstResult = first.join();
            Long secondResult = second.join();
            return firstResult + secondResult;
        } else {
            return countSum();
        }
    }

    private Long countSum() {
        return LongStream.range(startPoint, finishPoint).sum();
    }
}
