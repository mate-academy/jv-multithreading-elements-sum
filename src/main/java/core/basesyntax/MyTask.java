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
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            long result = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> second = new MyTask(middlePoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
