package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > THRESHOLD) {
            List<RecursiveTask> subTasks = createSubTasks();
            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> task : subTasks) {
                result += task.join();
            }
            return result;
        } else {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask> createSubTasks() {
        List<RecursiveTask> subTasks = new ArrayList<>();
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask first = new MyTask(startPoint, middlePoint);
        RecursiveTask second = new MyTask(middlePoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
