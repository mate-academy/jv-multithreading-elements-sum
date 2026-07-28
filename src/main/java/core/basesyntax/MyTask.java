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
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            int middle = (startPoint + finishPoint) / 2;
            MyTask task1 = new MyTask(startPoint, middle);
            MyTask task2 = new MyTask(middle, finishPoint);
            task1.fork();
            long task2Result = task2.compute();
            long task1Result = task1.join();

            return task1Result + task2Result;
        } else {
            long sum = 0L;
            for (int i = startPoint; i <= finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
