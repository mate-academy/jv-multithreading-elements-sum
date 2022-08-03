package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (Math.abs(finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createRecursiveSubTasks());
            for (RecursiveTask<Long> task : recursiveTasks) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> task : recursiveTasks) {
                result += task.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint)
                    .reduce(0, Long::sum);
        }
    }

    private List<RecursiveTask<Long>> createRecursiveSubTasks() {
        List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        recursiveTasks.add(first);
        recursiveTasks.add(second);
        return recursiveTasks;
    }
}
