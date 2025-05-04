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
        int range = finishPoint - startPoint;
        if (range <= THRESHOLD) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            System.out.println("Sum from " + startPoint + " to " + (finishPoint - 1) + " = " + sum);
            return sum;
        } else {
            int midPoint = (startPoint + finishPoint) / 2;
            MyTask subTask1 = new MyTask(startPoint, midPoint);
            MyTask subTask2 = new MyTask(midPoint, finishPoint);

            subTask1.fork();
            Long subTask2Result = subTask2.compute();
            Long subTask1Result = subTask1.join();

            return subTask1Result + subTask2Result;
        }
    }
}
