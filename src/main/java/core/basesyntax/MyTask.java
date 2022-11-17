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
        Long result = 0L;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> tasks = subTask();
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task : tasks) {
                result += task.join();
            }
        } else {
            result = LongStream.range(startPoint, finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> subTask() {
        int diff = (finishPoint - startPoint) / 2;
        List<RecursiveTask<Long>> subList = new ArrayList<>();
        MyTask first = new MyTask(startPoint, startPoint + diff);
        MyTask second = new MyTask(startPoint + diff, finishPoint);
        subList.add(first);
        subList.add(second);
        return subList;
    }
}
