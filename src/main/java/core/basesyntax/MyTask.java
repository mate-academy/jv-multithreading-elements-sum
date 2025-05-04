package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > 10) {
            int mid = (startPoint + finishPoint) / 2;
            MyTask left = new MyTask(startPoint, mid);
            MyTask right = new MyTask(mid, finishPoint);

            left.fork();
            right.fork();

            Long leftJoin = left.join();
            Long rightJoin = right.join();

            return leftJoin + rightJoin;
        } else {
            return calculate(startPoint, finishPoint);
        }
    }

    private Long calculate(int startPoint, int finishPoint) {
        long total = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            total += i;
        }
        return total;
    }
}
