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
        int workload = finishPoint - startPoint;
        long result = 0;
        if (workload <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        } else {
            int mid = (startPoint + finishPoint) / 2;
            MyTask first = new MyTask(startPoint, mid);
            MyTask second = new MyTask(mid, finishPoint);

            first.fork();
            second.fork();

            Long firstRes = first.join();
            Long secondRes = second.join();
            return firstRes + secondRes;
        }
    }
}
