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
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> task : recursiveTasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task : recursiveTasks) {
                result += task.join();
            }
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTask = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint,
                finishPoint - (finishPoint - startPoint) / 2);
        RecursiveTask<Long> second = new MyTask(finishPoint - (finishPoint - startPoint) / 2,
                finishPoint);
        subTask.add(first);
        subTask.add(second);
        return subTask;
    }
}
