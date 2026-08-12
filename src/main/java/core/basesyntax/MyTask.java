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
        if ((finishPoint - startPoint) < 10) {
            long j = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                j += i;
            }
            return j;
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask fistHalf = new MyTask(startPoint, middle);
            fistHalf.fork();
            MyTask secondHalf = new MyTask(middle, finishPoint);
            Long compute = secondHalf.compute();
            return fistHalf.join() + compute;

        }
    }
}
