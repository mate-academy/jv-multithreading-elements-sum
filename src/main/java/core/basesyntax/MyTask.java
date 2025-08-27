package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final Integer THRESHOLD = 10;

    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        if (startPoint >= finishPoint) {
            return 0L;
        }
        if (finishPoint - startPoint > 10) {
            int middle = (finishPoint + startPoint) / 2;
            RecursiveTask<Long> left = new MyTask(startPoint, middle);
            RecursiveTask<Long> right = new MyTask(middle, finishPoint);

            left.fork();
            right.fork();

            return left.join() + right.join();
        } else {
            return calculateSum(startPoint, finishPoint);
        }
    }

    private Long calculateSum(int startPoint, int finishPoint) {
        long result = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }
}
