package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int DISTANCE = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if ((finishPoint - startPoint) > DISTANCE) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            int tempStartPoint = startPoint;
            while (tempStartPoint < finishPoint) {
                result += tempStartPoint;
                tempStartPoint++;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTask = new ArrayList<>();
        int tempPoint = startPoint + DISTANCE;
        RecursiveTask<Long> first = new MyTask(startPoint, tempPoint);
        RecursiveTask<Long> second = new MyTask(tempPoint, finishPoint);
        subTask.add(first);
        subTask.add(second);
        return subTask;
    }
}
