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
        if (finishPoint - startPoint > 10) {
            long result = 0;
            List<RecursiveTask<Long>> subTasks = splitTask();
            for (RecursiveTask<Long> subTask: subTasks) {
                subTask.fork();
                result += subTask.join();
            }
            return result;

        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> splitTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> firstHalf = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> secondHalf = new MyTask(middlePoint, finishPoint);
        subTasks.add(firstHalf);
        subTasks.add(secondHalf);
        return subTasks;
    }
}
