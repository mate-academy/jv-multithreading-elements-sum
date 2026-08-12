package core.basesyntax;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAX_DISTANCE = 10;

    private int startPoint;
    private int finishPoint;
    private int distance;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        distance = finishPoint - startPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (distance > MAX_DISTANCE) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        }
        for (int i = startPoint; i < finishPoint; i++) {
            result += i;
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new LinkedList<>();
        int tmpPoint = startPoint + MAX_DISTANCE;
        RecursiveTask<Long> firstTask = new MyTask(startPoint, tmpPoint);
        RecursiveTask<Long> secondTask = new MyTask(tmpPoint, finishPoint);
        subTasks.add(firstTask);
        subTasks.add(secondTask);
        return subTasks;
    }
}
