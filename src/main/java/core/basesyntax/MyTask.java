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
            return findSum();
        } else {
            List<MyTask> subTasks = createSubTask();
            for (MyTask subTask : subTasks) {
                subTask.fork();
            }
            long sum = 0;
            for (MyTask subTask : subTasks) {
                sum += subTask.join();
            }
            return sum;
        }
    }

    private List<MyTask> createSubTask() {
        List<MyTask> sybTasks = new ArrayList<>();
        int mid = startPoint + (finishPoint - startPoint) / 2;
        MyTask left = new MyTask(startPoint, mid);
        MyTask right = new MyTask(mid, finishPoint);
        sybTasks.add(left);
        sybTasks.add(right);
        return sybTasks;
    }

    private Long findSum() {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
