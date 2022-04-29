package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int endPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.endPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (endPoint - startPoint >= 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            result = IntStream.range(startPoint, endPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, ((startPoint + endPoint) / 2));
        RecursiveTask<Long> second = new MyTask(((startPoint + endPoint) / 2), endPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
