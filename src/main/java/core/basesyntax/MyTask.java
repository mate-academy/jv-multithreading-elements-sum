package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        return isRecursively() ? recurs() : execute();
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int middlePoint = getMiddlePoint();
        List<RecursiveTask<Long>> recursiveTaskList = new ArrayList<>();
        recursiveTaskList.add(new MyTask(startPoint, middlePoint));
        recursiveTaskList.add(new MyTask(middlePoint, finishPoint));
        return recursiveTaskList;
    }

    private int getMiddlePoint() {
        return finishPoint - ((finishPoint - startPoint) / 2);
    }

    private boolean isRecursively() {
        return finishPoint - startPoint > 10;
    }

    private long recurs() {
        long result = 0;
        List<RecursiveTask<Long>> subTasks = createSubTasks();
        subTasks.forEach(ForkJoinTask::fork);
        for (RecursiveTask<Long> subTask : subTasks) {
            result += subTask.join();
        }
        return result;
    }

    private long execute() {
        long result = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }
}
