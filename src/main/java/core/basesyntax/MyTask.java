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
        if (finishPoint - startPoint <= THRESHOLD) {
            long result = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        } else {
            int middlePoint = startPoint + (finishPoint - startPoint) / 2;
            MyTask leftTask = new MyTask(startPoint, middlePoint);
            MyTask rightTask = new MyTask(middlePoint, finishPoint);
            leftTask.fork();
            rightTask.fork();
            Long rightResult = rightTask.join();
            Long leftResult = leftTask.join();
            return rightResult + leftResult;
        }
    }
}
