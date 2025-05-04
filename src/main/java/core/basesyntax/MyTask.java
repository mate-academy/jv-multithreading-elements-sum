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
        long result = 0L;
        if (workload <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask first = new MyTask(startPoint, middle);
            MyTask second = new MyTask(middle, finishPoint);

            first.fork();
            second.fork();

            Long firstResult = first.join();
            Long secondResult = second.join();
            return firstResult + secondResult;
        }
    }
}
