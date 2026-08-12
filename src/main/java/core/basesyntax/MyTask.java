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
        if (range < 10) {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
        int middle = startPoint + range / 2;
        MyTask leftTask = new MyTask(startPoint, middle);
        MyTask rightTask = new MyTask(middle, finishPoint);

        leftTask.fork();
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();

        return rightResult + leftResult;
    }
}
