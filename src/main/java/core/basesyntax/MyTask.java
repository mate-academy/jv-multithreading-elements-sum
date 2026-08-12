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
        if (finishPoint - startPoint > 10) {
            List<MyTask> subTasks = new ArrayList<>(createSubTasks());
            subTasks.forEach(ForkJoinTask::fork);
            return subTasks.stream().mapToLong(ForkJoinTask::join).sum();
        } else {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<MyTask> createSubTasks() {
        List<MyTask> subTasks = new ArrayList<>();
        MyTask first = new MyTask(startPoint, (finishPoint + startPoint) / 2);
        MyTask second = new MyTask((finishPoint + startPoint) / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
