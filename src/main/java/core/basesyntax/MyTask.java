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
        if ((startPoint > 0 && finishPoint - startPoint > 10)
                || (startPoint < 0 && finishPoint + startPoint > 10)) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                if (finishPoint - startPoint > 0) {
                    result += i;
                }
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, finishPoint / 2);
        RecursiveTask<Long> second = new MyTask(finishPoint / 2 + 1, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
