package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
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
        long count = 0;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask> subTasks = new ArrayList<>(creatSubTasks());
            subTasks.forEach(ForkJoinTask::fork);
            for (RecursiveTask<Long> subTask : subTasks) {
                count += subTask.join();
            }
            return count;
        }
        for (int i = startPoint; i < finishPoint; i++) {
            count += i;
        }
        return count;
    }

    private List<RecursiveTask> creatSubTasks() {
        List<RecursiveTask> subTasks = new ArrayList<>();
        int half = (startPoint + finishPoint) / 2;
        RecursiveTask first = new MyTask(startPoint, half);
        RecursiveTask second = new MyTask(half, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
