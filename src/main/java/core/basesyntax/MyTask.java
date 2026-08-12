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
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
            }
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        }
        return sum;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint / 2, finishPoint / 2);
        RecursiveTask<Long> second = new MyTask(startPoint / 2, finishPoint / 2);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
