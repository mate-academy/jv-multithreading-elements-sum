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
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> task : subTasks) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            long res = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                res += i;
            }
            return res;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTask = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + 10);
        RecursiveTask<Long> second = new MyTask(startPoint + 10, finishPoint);
        subTask.add(first);
        subTask.add(second);
        return subTask;
    }
}
