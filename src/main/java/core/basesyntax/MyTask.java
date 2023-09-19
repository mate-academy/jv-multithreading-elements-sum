package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        long result = 0;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subtask : subTasks) {
                subtask.fork();
            }
            for (RecursiveTask<Long> subtask : subTasks) {
                result += subtask.join();
            }
        } else {
            result = LongStream.range(startPoint, finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int halfPoint = (finishPoint + startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, halfPoint);
        RecursiveTask<Long> second = new MyTask(halfPoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
