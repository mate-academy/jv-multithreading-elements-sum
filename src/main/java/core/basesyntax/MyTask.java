package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private Long output = 0L;
    private final int step = 10;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (Math.abs(finishPoint) - Math.abs(startPoint) > step) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                output += subTask.join();
            }
            return output;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                output += i;
            }
            return output;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int from = startPoint;
        int to = startPoint + step;
        RecursiveTask<Long> first = new MyTask(from, to);
        if (Math.abs(finishPoint - to) < step) {
            to = finishPoint - step;
        }
        RecursiveTask<Long> second = new MyTask(to, finishPoint);
        finishPoint = finishPoint / 2;
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
