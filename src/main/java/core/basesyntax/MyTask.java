package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private int difference = finishPoint - startPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (difference > 10) {
            long result = 0;
            List<RecursiveTask<Long>> subtasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subtask : subtasks) {
                subtask.fork();
                result += subtask.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int middlePoint = finishPoint / 2;
        List<RecursiveTask<Long>> subtasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> second = new MyTask(middlePoint + 1, finishPoint);
        subtasks.add(first);
        subtasks.add(second);
        return subtasks;
    }
}
