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

        if (range <= 10) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int midPoint = startPoint + range / 2;

            MyTask firstSubtask = new MyTask(startPoint, midPoint);
            MyTask secondSubtask = new MyTask(midPoint, finishPoint);

            firstSubtask.fork();
            secondSubtask.fork();

            long firstResult = firstSubtask.join();
            long secondResult = secondSubtask.join();

            return firstResult + secondResult;
        }
    }
}
