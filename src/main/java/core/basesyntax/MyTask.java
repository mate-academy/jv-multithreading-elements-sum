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
        if ((finishPoint - startPoint) > 10) {
            fork();
        }
        long result = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }
}
