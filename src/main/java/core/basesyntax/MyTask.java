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
        if (finishPoint - startPoint <= 10) {
            long resultSum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                resultSum += i;
            }
            return resultSum;
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask first = new MyTask(startPoint, middle);
            MyTask second = new MyTask(middle, finishPoint);

            first.fork();
            second.fork();

            long firstResult = first.join();
            long secondResult = second.join();

            return firstResult + secondResult;
        }
    }
}
