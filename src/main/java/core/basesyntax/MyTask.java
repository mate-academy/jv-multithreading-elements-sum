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
            MyTask firstTask = new MyTask(startPoint, startPoint
                    + (finishPoint - startPoint) / 2);
            MyTask secondTask = new MyTask(startPoint
                    + (finishPoint - startPoint) / 2, finishPoint);

            firstTask.fork();
            secondTask.fork();

            return firstTask.join() + secondTask.join();
        } else {
            int result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return (long) result;
        }
    }
}
