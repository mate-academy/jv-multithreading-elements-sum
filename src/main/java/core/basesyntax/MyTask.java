package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private Long finalResult = 0L;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint < startPoint) {
            return 0L;
        }
        if (finishPoint == 0) {
            return 0L;
        }
        for (int i = startPoint; i < finishPoint; i++) {
            finalResult += (long) i;
        }
        return finalResult;
    }
}
