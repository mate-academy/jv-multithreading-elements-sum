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
        long sum = 0;
        if (finishPoint - startPoint <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int mid = (startPoint + finishPoint) / 2;
            MyTask first = new MyTask(startPoint, mid);
            MyTask second = new MyTask(mid, finishPoint);
            Long firstResult = first.fork().join();
            Long secondResult = second.fork().join();
            return firstResult + secondResult;
        }
    }
}
