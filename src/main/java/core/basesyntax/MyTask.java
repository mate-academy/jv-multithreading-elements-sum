package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        List<RecursiveTask<Long>> subTasks;
        if (finishPoint - startPoint <= THRESHOLD) {
            return findSum(startPoint, finishPoint);
        } else {
            int middlePoint = (startPoint + finishPoint) / 2;
            RecursiveTask<Long> left = new MyTask(startPoint, middlePoint);
            RecursiveTask<Long> right = new MyTask(middlePoint, finishPoint);
            left.fork();
            right.fork();
            long leftResult = left.join();
            long rightResult = right.join();
            return leftResult + rightResult;
        }
    }

    private long findSum(int startPoint, int finishPoint) {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
