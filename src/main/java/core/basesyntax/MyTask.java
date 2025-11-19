package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 10;

    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        int n = finishPoint - startPoint;
        if (n > THRESHOLD) {
            int mid = startPoint + n / 2;
            MyTask left = new MyTask(startPoint, mid);
            MyTask right = new MyTask(mid, finishPoint);

            left.fork();
            return right.compute() + left.join();
        /*
            left.fork();
            right.fork();
            return left.join() + right.join();*/

        /*
            invokeAll(left, right);
            return left.join() + right.join();*/
        } else {
            return getSum();
        }
    }

    private Long getSum() {
        long sum = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
