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
        if (countDistance() > 10) {
            MyTask first = new MyTask(startPoint, (finishPoint + startPoint) / 2);
            MyTask second = new MyTask((finishPoint + startPoint) / 2, finishPoint);
            first.fork();
            long secondResult = second.compute();
            long firstResult = first.join();
            return secondResult + firstResult;
        } else {
            return countResult();
        }
    }

    private int countDistance() {
        return finishPoint - startPoint;
    }

    private Long countResult() {
        int sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return (long) sum;
    }

}
