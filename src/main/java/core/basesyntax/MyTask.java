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
            long result = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return (long) result;
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);

            left.fork();
            right.fork();

            Long result = 0L;
            result += left.join();
            result += right.join();

            return result;
        }
    }
}
