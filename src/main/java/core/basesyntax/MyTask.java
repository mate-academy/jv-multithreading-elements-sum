package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final long MAX_DISTANCE = 10;

    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;

        if (finishPoint - startPoint > MAX_DISTANCE) {
            List<RecursiveTask<Long>> subTasks = createSubTasksList();
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

    private List<RecursiveTask<Long>> createSubTasksList() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int midPoint = (startPoint + finishPoint) / 2;
        subTasks.add(new MyTask(startPoint, midPoint));
        subTasks.add(new MyTask(midPoint, finishPoint));
        return subTasks;
    }
}
