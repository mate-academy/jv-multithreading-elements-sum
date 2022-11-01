package core.basesyntax;

import java.util.ArrayList;
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
        long result = 0;
        if ((finishPoint-startPoint) > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            for (long i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int distance = (finishPoint + startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, distance);
        RecursiveTask<Long> second = new MyTask(distance, finishPoint);
        return List.of(first, second);
    }
}
