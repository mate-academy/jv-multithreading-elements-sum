package core.basesyntax;

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
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> recursiveTasks = createSubTasks();
            for (RecursiveTask<Long> recursiveTask: recursiveTasks) {
                recursiveTask.fork();
                result += recursiveTask.join();
            }
        } else {
            result = (long) IntStream.range(startPoint, finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int nextPoint = startPoint + 9;
        RecursiveTask<Long> first = new MyTask(startPoint, nextPoint);
        RecursiveTask<Long> sec = new MyTask(nextPoint, finishPoint);
        return List.of(first, sec);
    }
}
