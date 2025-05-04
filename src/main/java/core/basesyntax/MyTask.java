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
            List<RecursiveTask> sunTask = new ArrayList<>(createSubTasks());
            for (RecursiveTask recursiveTask : sunTask) {
                recursiveTask.fork();
            }
        }
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum = sum + startPoint;
            startPoint++;
        }
        return sum;
    }

    private List<RecursiveTask> createSubTasks() {
        List<RecursiveTask> sunTask = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint / 2, finishPoint / 2);
        RecursiveTask<Long> second = new MyTask(startPoint / 2, finishPoint / 2);
        sunTask.add(first);
        sunTask.add(second);
        return sunTask;
    }
}
