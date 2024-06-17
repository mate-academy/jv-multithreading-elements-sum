package core.basesyntax;

import java.util.concurrent.ForkJoinPool;
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
            return computeDirectly();
        } else {
            int mid = startPoint + range / 2;
            MyTask subTask1 = new MyTask(startPoint, mid);
            MyTask subTask2 = new MyTask(mid, finishPoint);
            subTask1.fork();
            long result2 = subTask2.compute();
            long result1 = subTask1.join();
            long result = result1 + result2;
            System.out.println("Merging results: " + result1 + " + " + result2 + " = " + result);
            return result;
        }
    }

    private Long computeDirectly() {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
