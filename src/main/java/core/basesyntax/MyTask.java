package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
        // write your code here
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        }
        return additionSubTask();
    }

    private long additionSubTask() {
        long result = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int lengthPoint = finishPoint - startPoint;
        RecursiveTask<Long> firstTask = new MyTask(startPoint, startPoint + lengthPoint/2 - 1);
        RecursiveTask<Long> secondTask = new MyTask(startPoint + lengthPoint/2, finishPoint);
        subTasks.add(firstTask);
        subTasks.add(secondTask);
        return subTasks;
    }

}
