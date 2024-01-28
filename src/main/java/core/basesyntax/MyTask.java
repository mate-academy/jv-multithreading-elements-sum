package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private int distance = finishPoint - startPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (distance > 10) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
        }
        long sum = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;

    }

    private List<RecursiveTask<Long>> createSubTasks() {
        RecursiveTask<Long> first = new MyTask(startPoint, finishPoint / 2);
        RecursiveTask<Long> second = new MyTask(finishPoint / 2 + 1, finishPoint);
        return List.of(first, second);
    }
}
