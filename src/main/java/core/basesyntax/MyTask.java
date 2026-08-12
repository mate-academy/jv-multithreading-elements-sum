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
        if (finishPoint < startPoint || finishPoint == startPoint) {
            return 0L;
        }
        int workLoad = finishPoint - startPoint;
        long result = 0;
        if (workLoad > 10) {
            List<RecursiveTask<Long>> subTasks
                    = new ArrayList<>(createSubTasks(workLoad));
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks(int workLoad) {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int subTaskWorkLoad = workLoad / 2;
        int middleValue = startPoint + subTaskWorkLoad;
        RecursiveTask<Long> firstSubTask = new MyTask(startPoint, middleValue);
        RecursiveTask<Long> secondSubTask = new MyTask(middleValue, finishPoint);
        subTasks.add(firstSubTask);
        subTasks.add(secondSubTask);
        return subTasks;
    }
}
