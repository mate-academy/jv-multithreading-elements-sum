package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final int startPoint;
    private final int finishPoint;

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

            MyTask firstSubTask = new MyTask(startPoint, midPoint);
            MyTask secondSubTask = new MyTask(midPoint, finishPoint);

            firstSubTask.fork();
            secondSubTask.fork();

            return firstSubTask.join() + secondSubTask.join();
        }
    }
}
