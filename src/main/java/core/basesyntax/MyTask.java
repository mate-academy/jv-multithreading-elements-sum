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
        long distance = finishPoint - startPoint;
        if (distance <= 10) {
            return computeDirectly();
        } else {
            long mid = startPoint + distance / 2;
            MyTask leftSubtask = new MyTask(startPoint, (int) mid);
            MyTask rightSubtask = new MyTask((int) mid, finishPoint);
            leftSubtask.fork();
            rightSubtask.fork();
            long leftResult = leftSubtask.join();
            long rightResult = rightSubtask.join();
            return leftResult + rightResult;
        }
    }

    private long computeDirectly() {
        long sum = 0;
        for (long i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
