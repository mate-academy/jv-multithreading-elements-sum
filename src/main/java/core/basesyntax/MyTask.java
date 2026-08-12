package core.basesyntax;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
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
        var distance = finishPoint - startPoint;
        if (distance <= THRESHOLD) {
            return calculateSum(startPoint, finishPoint);
        } else {
            List<RecursiveTask<Long>> tasks = initSubTasks();
            tasks.forEach(ForkJoinTask::fork);
            return tasks.stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }
    }

    private List<RecursiveTask<Long>> initSubTasks() {
        var middle = (startPoint + finishPoint) / 2;
        List<RecursiveTask<Long>> tasks = new LinkedList<>();
        var firstTask = new MyTask(startPoint, middle);
        var secondTask = new MyTask(middle, finishPoint);
        tasks.add(firstTask);
        tasks.add(secondTask);
        return tasks;
    }

    private long calculateSum(int start, int finish) {
        var sum = 0;
        for (int i = start; i < finish; i++) {
            sum += i;
        }
        return sum;
    }
}
