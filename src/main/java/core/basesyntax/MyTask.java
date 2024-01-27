package core.basesyntax;

import java.util.List;
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
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subs = subTask();
            for (RecursiveTask<Long> sub : subs) {
                sub.fork();
            }
        }
        long result = 0L;
        for (long i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }

    private List<RecursiveTask<Long>> subTask() {
        RecursiveTask<Long> first = new MyTask(startPoint, finishPoint / 2);
        RecursiveTask<Long> second = new MyTask(finishPoint / 2, finishPoint);
        return List.of(first, second);
    }
}
