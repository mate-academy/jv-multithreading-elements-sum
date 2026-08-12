package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAX_DISTANCE = 10;
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
            List<MyTask> subTasks = createSubTasks();
            for (MyTask subTask : subTasks) {
                subTask.fork();
            }
            for (MyTask subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<MyTask> createSubTasks() {
        List<MyTask> subTasks = new ArrayList<>();
        int distance = (finishPoint - startPoint) / 2;
        subTasks.add(new MyTask(startPoint, finishPoint - distance));
        subTasks.add(new MyTask(finishPoint - distance, finishPoint));
        return subTasks;
    }
}
