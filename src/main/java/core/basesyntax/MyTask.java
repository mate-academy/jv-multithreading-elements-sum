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
        if ((finishPoint - startPoint) <= 10) {
            long s = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                s += i;
            }
            return s;

        } else {
            int mid = (startPoint + finishPoint) / 2;

            MyTask left = new MyTask(startPoint, mid);
            MyTask right = new MyTask(mid, finishPoint);

            left.fork();
            long rightRes = right.compute();
            long leftRes = left.join();
            return leftRes + rightRes;
        }
    }
}
