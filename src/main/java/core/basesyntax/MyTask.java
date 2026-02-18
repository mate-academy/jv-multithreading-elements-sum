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
        if ((finishPoint - startPoint) < 10) {
            return calculateSum(startPoint, finishPoint);
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);
            left.fork();
            Long rightResult = right.compute();
            Long leftResult = left.join();
            return rightResult + leftResult;
        }
    }

    private Long calculateSum(int startPoint, int finishPoint) {
        int sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum = sum + i;
        }
        return Long.valueOf(sum);
    }
}
