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
        int distance = finishPoint - startPoint;
        long result = 0;
        if (distance >= 10) {
            int mid = (startPoint + finishPoint) / 2;
            MyTask first = new MyTask(startPoint, mid);
            MyTask second = new MyTask(mid, finishPoint);

            first.fork();
            second.fork();

            return first.join() + second.join();
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }
}
