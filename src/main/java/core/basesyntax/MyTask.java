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
        long sum = 0;
        if (Math.abs(finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(splitToSubtasks());
            for (RecursiveTask<Long> subtask : subTasks) {
                subtask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                sum += subTask.join();
            }
            return sum;
        }
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }

    private List<RecursiveTask<Long>> splitToSubtasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = Math.abs(finishPoint - startPoint) / 2;
        subTasks.add(new MyTask(startPoint, startPoint + middlePoint));
        subTasks.add(new MyTask(startPoint + middlePoint, finishPoint));
        return subTasks;

    }
}
