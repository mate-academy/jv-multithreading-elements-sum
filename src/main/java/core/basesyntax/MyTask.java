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
        Long finalResult = 0L;
        if (finishPoint - startPoint > THRESHOLD) {
            int midPoint = (finishPoint + startPoint) / 2;

            MyTask firstHalfTask = new MyTask(startPoint, midPoint);
            MyTask secondHalfTask = new MyTask(midPoint, finishPoint);

            firstHalfTask.fork();

            Long secondResult = secondHalfTask.compute();
            Long firstResult = firstHalfTask.join();

            finalResult = firstResult + secondResult;
            return finalResult;
        }

        for (int i = 0; i < finishPoint - startPoint; i++) {
            finalResult += startPoint + i;
        }

        return finalResult;
    }
}
