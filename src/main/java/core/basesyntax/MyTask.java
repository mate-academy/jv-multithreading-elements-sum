package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private int sum;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (startPoint < finishPoint) {
            while (startPoint != finishPoint) {
                sum += startPoint;
                startPoint++;
            }
            return (long) sum;
        }
        return 0L;
    }
}
