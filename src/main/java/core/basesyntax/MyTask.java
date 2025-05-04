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
        int localStartPoint = startPoint;
        Long sum = 0L;
        if (localStartPoint > finishPoint) {
            return sum;
        }
        if (localStartPoint - finishPoint > 10
                || finishPoint - localStartPoint > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task : subTasks) {
                sum += task.join();
            }
            return sum;
        } else {
            while (localStartPoint != finishPoint) {
                sum += localStartPoint;
                localStartPoint++;
            }
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + 9);
        RecursiveTask<Long> second = new MyTask(startPoint + 9, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
