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

        long quantity = finishPoint - startPoint;

        if (quantity > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask: subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask: subTasks) {
                result += subTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int quantity = finishPoint - startPoint;
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + (quantity / 2));
        RecursiveTask<Long> second = new MyTask(startPoint + (quantity / 2), finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
