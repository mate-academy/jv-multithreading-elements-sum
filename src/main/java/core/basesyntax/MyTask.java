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
        long result = 0;
        if (finishPoint - startPoint <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        } else {
            int mid = (startPoint + finishPoint) / 2;
            MyTask firstTask = new MyTask(startPoint, mid);
            MyTask secondTask = new MyTask(mid, finishPoint);
            firstTask.fork();
            secondTask.fork();
            result = firstTask.join() + secondTask.join();
        }
        return result;
    }
}
