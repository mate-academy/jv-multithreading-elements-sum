package core.basesyntax;

import java.util.Arrays;
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
        if (finishPoint - startPoint < 10) {
            return findSum(startPoint, finishPoint);
        } else {
            int middlePoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middlePoint);
            MyTask rightTask = new MyTask(middlePoint, finishPoint);
            leftTask.fork();
            rightTask.fork();
            Long leftResult = leftTask.join();
            Long rightResult = rightTask.join();
            return leftResult + rightResult;
        }
    }

    private Long findSum(int startPoint, int finishPoint) {
        long[] array = LongStream.range(startPoint, finishPoint).toArray();
        return Arrays.stream(array).sum();
    }
}
