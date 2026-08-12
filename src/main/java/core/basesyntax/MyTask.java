package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final Integer THRESHOLD = 10;

    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (startPoint >= finishPoint) {
            return 0L;
        }
        if (finishPoint - startPoint <= THRESHOLD) {
            return ((long) (finishPoint - startPoint) * (finishPoint - 1 + startPoint) / 2);
        } else {
            int middle = (finishPoint + startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);

            left.fork();
            right.fork();

            long result = 0;
            result += left.join();
            result += right.join();

            return result;
        }
    }
}
