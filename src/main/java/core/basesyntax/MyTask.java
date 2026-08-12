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
        long result = 0;
        if (finishPoint - startPoint > THRESHOLD) {
            List<MyTask> subTask = createSubTask();
            invokeAll(subTask);
            for (MyTask task : subTask) {
                result += task.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<MyTask> createSubTask() {
        List<MyTask> subTask = new ArrayList<>();
        int middle = (startPoint + finishPoint) / 2;
        subTask.add(new MyTask(startPoint, middle));
        subTask.add(new MyTask(middle, finishPoint));
        return subTask;
    }
}
