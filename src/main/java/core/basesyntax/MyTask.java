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
        if (finishPoint - startPoint > 10) {
            int mid = (finishPoint + startPoint) / 2;
            MyTask firstTask = new MyTask(startPoint, mid);
            MyTask secondTask = new MyTask(mid, finishPoint);
            firstTask.fork();
            secondTask.fork();

            long result = 0;
            result += firstTask.join();
            result += secondTask.join();
            return result;
        } else {
            return countSum(startPoint, finishPoint);
        }
    }

    private long countSum(int startPoint, int finishPoint) {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
