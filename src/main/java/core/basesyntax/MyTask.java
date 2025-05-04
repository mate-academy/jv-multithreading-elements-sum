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
        int range = finishPoint - startPoint;
        if (range > 0 && range <= 10) {
            Long sum = 0L;
            for (int i = startPoint; i < (startPoint + range); i++) {
                sum += i;
            }
            return sum;
        }
        if (range > 10) {
            int mid = startPoint + range / 2;
            MyTask left = new MyTask(startPoint, mid);
            MyTask right = new MyTask(mid, finishPoint);
            left.fork();
            Long leftResult = left.join();
            Long rightResult = right.compute();
            return leftResult + rightResult;
        }
        return 0L;
    }
}
