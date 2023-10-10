package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int SIZE_EDGE = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint <= SIZE_EDGE) {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
        List<RecursiveTask<Long>> subTasks = createSubTasks();
        for (RecursiveTask<Long> subTask : subTasks
             ) {
            subTask.fork();
        }
        long result = 0;
        for (RecursiveTask<Long> subTask : subTasks
        ) {
            result += subTask.join();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middle = (startPoint + finishPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, middle);
        RecursiveTask<Long> second = new MyTask(middle, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
