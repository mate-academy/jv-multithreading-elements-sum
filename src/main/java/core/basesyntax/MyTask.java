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
        // write your code here
        long sum = 0;
        if (finishPoint - startPoint <= THRESHOLD) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int midlPoint = (startPoint + finishPoint) / 2;
            MyTask firstTask = new MyTask(startPoint, midlPoint);
            MyTask secondTask = new MyTask(midlPoint, finishPoint);
            firstTask.fork();
            secondTask.fork();
            long firstResult = firstTask.join();
            long secondResult = secondTask.join();
            return firstResult + secondResult;
        }
    }
}
