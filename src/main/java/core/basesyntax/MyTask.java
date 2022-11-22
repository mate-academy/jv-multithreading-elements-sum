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
        int workload = finishPoint - startPoint;
        long result = 0;
        if (workload > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask: subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask: subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int workload = (finishPoint - startPoint) / 2;
        List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint + workload, finishPoint);
        RecursiveTask<Long> second = new MyTask(startPoint, startPoint + workload);
        recursiveTasks.add(first);
        recursiveTasks.add(second);
        return recursiveTasks;
    }
}
