package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (calculateArraySize() <= THRESHOLD) {
            return calculateSumFromPoints();
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;

            MyTask leftSubTask = new MyTask(startPoint, middle - 1);
            MyTask rightSubTask = new MyTask(middle, finishPoint);

            leftSubTask.fork();
            rightSubTask.fork();

            Long leftJoin = leftSubTask.join();
            Long rightJoin = rightSubTask.join();

            return Math.addExact(leftJoin, rightJoin);
        }
    }

    private long calculateSumFromPoints() {
        long sum = 0;
        while (startPoint <= finishPoint) {
            sum += startPoint++;
        }
        return sum;
    }

    private int calculateArraySize() {
        return finishPoint - startPoint;
    }
}
