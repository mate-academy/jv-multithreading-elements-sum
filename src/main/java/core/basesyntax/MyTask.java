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
        if (finishPoint - startPoint <= THRESHOLD) {
            return countSum();
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask firstTask = new MyTask(startPoint, middle);
            MyTask secondTask = new MyTask(middle, finishPoint);

            firstTask.fork();
            secondTask.fork();

            Long firstResult = firstTask.join();
            Long secondResult = secondTask.join();

            return firstResult + secondResult;
        }
    }

    private Long countSum() {
        Long sum = 0L;
        for (long i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
