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
            return Long.valueOf(getSum(startPoint, finishPoint));
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;

            MyTask first = new MyTask(startPoint, middle);
            MyTask second = new MyTask(middle, finishPoint);

            first.fork();
            second.fork();
            Long firstSum = first.join();
            Long secondSum = second.join();

            return firstSum + secondSum;
        }
    }

    private int getSum(int start, int end) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += i;
        }
        return sum;
    }
}
