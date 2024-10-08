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
        int range = finishPoint - startPoint;
        if (range > 10) {
            int midPoint = (startPoint + finishPoint) / 2;
            MyTask subTask1 = new MyTask(startPoint, midPoint);
            MyTask subTask2 = new MyTask(midPoint, finishPoint);
            invokeAll(subTask1, subTask2);

            long result1 = subTask1.join();
            long result2 = subTask2.join();

            return result1 + result2;
        } else {
            return computeDirectly();
        }
    }

    private Long computeDirectly() {
        long sum = 0;
        for (long i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        System.out.println("Computed sum from "
                + startPoint + " to "
                + finishPoint + " = " + sum);
        return sum;
    }
}
