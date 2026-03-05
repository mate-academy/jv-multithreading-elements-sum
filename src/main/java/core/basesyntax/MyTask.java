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
            Long sum = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;

        MyTask left = new MyTask(startPoint, middlePoint);
        MyTask right = new MyTask(middlePoint, finishPoint);

        left.fork();
        Long rightResult = right.compute();
        Long leftResult = left.join();

        return leftResult + rightResult;
    }
}
