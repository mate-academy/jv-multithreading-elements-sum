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
        long sum = 0;
        if (finishPoint - startPoint < 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
        List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
        for (RecursiveTask<Long> subTask : subTasks) {
            subTask.fork();
        }
        for (RecursiveTask<Long> subTask : subTasks) {
            sum += subTask.join();
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
