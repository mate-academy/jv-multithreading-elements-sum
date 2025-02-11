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
            return sum(startPoint, finishPoint);
        } else {
            int mid = startPoint + (finishPoint - startPoint) / 2;

            MyTask task1 = new MyTask(startPoint, mid);
            MyTask task2 = new MyTask(mid, finishPoint);

            task1.fork();
            long rightResult = task2.compute();
            long leftResult = task1.join();

            return leftResult + rightResult;
        }
    }

    private Long sum(int start, int end) {
        long sum = 0L;
        for (int i = start; i < end; i++) {
            sum += i;
        }
        return sum;
    }
}
