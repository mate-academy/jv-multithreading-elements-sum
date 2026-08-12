package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (startPoint > finishPoint) {
            return 0L;
        }
        Long result = 0L;
        if (Math.abs(finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> recursiveTaskList = createSubTasks();
            for (RecursiveTask<Long> task : recursiveTaskList) {
                task.fork();
            }
            for (RecursiveTask<Long> task : recursiveTaskList) {
                result += task.join();
            }
        } else {
            result = (long) IntStream.range(startPoint, finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int separator = startPoint + 9;
        RecursiveTask<Long> first = new MyTask(startPoint, separator);
        RecursiveTask<Long> second = new MyTask(separator, finishPoint);
        return new ArrayList<>(List.of(first, second));
    }
}
