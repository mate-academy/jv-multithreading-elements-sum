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
        long result = 0L;

        if (finishPoint - startPoint > THRESHOLD) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();

            for (RecursiveTask<Long> recursiveTask : subTasks) {
                recursiveTask.fork();
            }

            for (RecursiveTask<Long> recursiveTask : subTasks) {
                result += recursiveTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        final int avarage = (startPoint + finishPoint) / 2;

        RecursiveTask<Long> left = new MyTask(startPoint, avarage);
        RecursiveTask<Long> right = new MyTask(avarage, finishPoint);
        tasks.add(left);
        tasks.add(right);
        return tasks;
    }
}
