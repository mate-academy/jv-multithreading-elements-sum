package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int length = finishPoint - startPoint;
        if (length > THRESHOLD) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            Long result = 0L;
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> recursiveAction : subTasks) {
                result += recursiveAction.join();
            }
            return result;
        } else {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int midPoint = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> leftTask = new MyTask(startPoint, midPoint);
        RecursiveTask<Long> rightTask = new MyTask(midPoint, finishPoint);
        subTasks.add(leftTask);
        subTasks.add(rightTask);
        return subTasks;
    }

}
