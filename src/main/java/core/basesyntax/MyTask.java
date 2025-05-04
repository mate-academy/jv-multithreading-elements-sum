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
        if (finishPoint - startPoint < THRESHOLD) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            MyTask first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
            MyTask second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
            first.fork();
            second.fork();
            Long firstResult = first.join();
            Long secondResult = second.join();
            return firstResult + secondResult;
        }
    }
}
