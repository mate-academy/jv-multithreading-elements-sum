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
            return calculate(startPoint, finishPoint);
        } else {
            int mid = (startPoint + finishPoint) / 2;
            MyTask subTask1 = new MyTask(startPoint, mid);
            MyTask subTask2 = new MyTask(mid, finishPoint);

            // Fork subtasks
            subTask1.fork();
            subTask2.fork();

            // Join results of subtasks
            long result1 = subTask1.join();
            long result2 = subTask2.join();

            // Combine results
            return result1 + result2;
        }
    }

    private Long calculate(int startPoint, int finishPoint) {
        long result = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }
}
