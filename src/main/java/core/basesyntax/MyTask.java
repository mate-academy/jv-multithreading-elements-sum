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
        if (startPoint > finishPoint) {
            return 0L;
        }
        if (Math.abs(finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
            }
            long sum = 0;
            for (RecursiveTask<Long> task : subTasks) {
                sum += task.join();
            }
            return sum;
        } else {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = Math.abs(finishPoint - startPoint) / 2;
        subTasks.add(new MyTask(startPoint, startPoint + middlePoint));
        subTasks.add(new MyTask(startPoint + middlePoint, finishPoint));
        return subTasks;
    }
}
