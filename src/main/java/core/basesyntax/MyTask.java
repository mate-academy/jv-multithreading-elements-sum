package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

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
        if (startPoint > finishPoint) {
            return 0L;
        }
        if (Math.abs(finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> tasks = createSubTasks();
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task : tasks) {
                result += task.join();
            }
        } else {
            result = (long) IntStream.range(startPoint, finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int sep = startPoint + 9;
        RecursiveTask<Long> first = new MyTask(startPoint, sep);
        RecursiveTask<Long> sec = new MyTask(sep, finishPoint);
        return new ArrayList<>(List.of(first, sec));
    }
}
