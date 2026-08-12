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
        if (finishPoint - startPoint <= 10) {
            long sum = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
        int middle = (startPoint + finishPoint) / 2;
        MyTask firstHalf = new MyTask(startPoint, middle);
        firstHalf.fork();
        MyTask secondHalf = new MyTask(middle, finishPoint);
        return secondHalf.compute() + firstHalf.join();
    }
}
