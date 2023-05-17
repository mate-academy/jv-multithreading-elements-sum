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
        long result = 0L;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(getSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            result = IntStream.range(startPoint, finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> getSubTasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        int intermediatePoint = (startPoint + finishPoint) / 2;
        RecursiveTask<Long> taskOne = new MyTask(startPoint, intermediatePoint);
        RecursiveTask<Long> taskTwo = new MyTask(intermediatePoint, finishPoint);
        tasks.add(taskOne);
        tasks.add(taskTwo);
        return tasks;
    }
}
