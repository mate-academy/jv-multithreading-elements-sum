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
        // Base case: Direct calculation for small ranges
        if (finishPoint - startPoint <= 10) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int midPoint = (startPoint + finishPoint) / 2;
            MyTask subtask1 = new MyTask(startPoint, midPoint);
            MyTask subtask2 = new MyTask(midPoint, finishPoint);

            subtask1.fork();
            long subtask2Result = subtask2.compute();
            long subtask1Result = subtask1.join();

            return subtask1Result + subtask2Result;
        }
    }
}
