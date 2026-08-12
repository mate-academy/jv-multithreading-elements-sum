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
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> recursiveAction: subTasks) {
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
        RecursiveTask<Long> first = new MyTask(startPoint, midPoint);
        RecursiveTask<Long> second = new MyTask(midPoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
