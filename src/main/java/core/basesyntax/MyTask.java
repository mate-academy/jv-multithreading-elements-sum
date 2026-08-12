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
        long result = 0L;
        if ((finishPoint - startPoint) > 10) {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MyTask left = new MyTask(startPoint, middle);
            MyTask right = new MyTask(middle, finishPoint);

            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();

            result = rightResult + leftResult;
            return result;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }
}
