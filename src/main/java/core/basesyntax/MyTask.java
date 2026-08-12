package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> tasks = new ArrayList<>(splitTask());
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task : tasks) {
                result += task.join();
            }
        } else {
            for (long i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> splitTask() {
        int diff = (startPoint + finishPoint) / 2;
        RecursiveTask<Long> firstTask = new MyTask(startPoint, diff);
        RecursiveTask<Long> secondTask = new MyTask(diff, finishPoint);
        return List.of(firstTask, secondTask);
    }
}
