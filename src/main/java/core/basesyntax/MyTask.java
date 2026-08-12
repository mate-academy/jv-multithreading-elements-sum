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
        if (finishPoint - startPoint <= THRESHOLD) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int midPoint = (startPoint + finishPoint) / 2;
            MyTask firstTask = new MyTask(startPoint, midPoint);
            MyTask secondTask = new MyTask(midPoint, finishPoint);

            firstTask.fork();
            secondTask.fork();

            long firstTaskResult = firstTask.join();
            long secondTaskResult = secondTask.join();

            return firstTaskResult + secondTaskResult;
        }
    }
}
