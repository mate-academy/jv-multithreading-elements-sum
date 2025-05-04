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
        if (finishPoint - startPoint <= THRESHOLD) {
            return doWork();
        } else {
            List<MyTask> subTasks = createSubTasks();
            invokeAll(subTasks);

            long result = 0;
            for (MyTask subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        }
    }

    private List<MyTask> createSubTasks() {
        List<MyTask> subTasks = new ArrayList<>();
        int mid = (startPoint + finishPoint) / 2;
        subTasks.add(new MyTask(startPoint, mid));
        subTasks.add(new MyTask(mid, finishPoint));
        return subTasks;
    }

    private long doWork() {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
