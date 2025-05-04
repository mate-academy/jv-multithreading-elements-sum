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
        if (finishPoint - startPoint > 10) {
            int middlePoint = (startPoint + finishPoint) / 2;
            MyTask firstTask = new MyTask(startPoint, middlePoint);
            MyTask secondTask = new MyTask(middlePoint, finishPoint);
            firstTask.fork();
            long secondResult = secondTask.compute();
            long firstResult = firstTask.join();
            return firstResult + secondResult;
        } else {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
