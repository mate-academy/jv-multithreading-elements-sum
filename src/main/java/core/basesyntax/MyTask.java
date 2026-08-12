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
        long result = 0;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> recursiveTaskList = new ArrayList<>(recursiveTasks());
            for (RecursiveTask<Long> task : recursiveTaskList) {
                task.fork();
            }
            for (RecursiveTask<Long> task : recursiveTaskList) {
                result += task.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> recursiveTasks() {
        List<RecursiveTask<Long>> recursiveTaskList = new ArrayList<>();
        int middlePoint = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> second = new MyTask(middlePoint, finishPoint);
        recursiveTaskList.add(first);
        recursiveTaskList.add(second);
        return recursiveTaskList;
    }
}
