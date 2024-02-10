package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint < startPoint) {
            return 0L;
        }
        long result = 0;
        if (finishPoint - startPoint > THRESHOLD) {
            List<RecursiveTask<Long>> subTask = createSubTask();
            for (RecursiveTask<Long> longRecursiveTask : subTask) {
                longRecursiveTask.fork();
            }
            for (RecursiveTask<Long> longRecursiveTask : subTask) {
                result += longRecursiveTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        int mid = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, mid);
        RecursiveTask<Long> second = new MyTask(mid, finishPoint);
        return List.of(first, second);
    }
}
