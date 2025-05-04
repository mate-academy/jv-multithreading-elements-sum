package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int STEP = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (finishPoint - startPoint > STEP) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            for (long i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int startPointPlusStep = startPoint + STEP;
        RecursiveTask<Long> firstTask = new MyTask(startPoint, startPointPlusStep);
        RecursiveTask<Long> secondTask = new MyTask(startPointPlusStep, finishPoint);
        subTasks.add(firstTask);
        subTasks.add(secondTask);
        return subTasks;
    }
}
