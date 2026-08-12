package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int distance = finishPoint - startPoint;
        // If the range is small enough, calculate directly
        if (distance <= 10) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            System.out.println("Sum from " + startPoint + " to " + finishPoint + " is " + sum);
            return sum;
        } else {
            // Split the task into two subtasks
            int midPoint = startPoint + distance / 2;
            MyTask subtask1 = new MyTask(startPoint, midPoint);
            MyTask subtask2 = new MyTask(midPoint, finishPoint);
            // Fork the subtasks to run them in parallel
            subtask1.fork();
            subtask2.fork();
            // Join the results from the subtasks
            long result1 = subtask1.join();
            long result2 = subtask2.join();
            // Merge the results
            long finalResult = result1 + result2;
            System.out.println("Merging results from " + startPoint + " to " + finishPoint + ": "
                    + result1 + " + " + result2 + " = " + finalResult);
            return finalResult;
        }
    }
}
