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
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            System.out.println("Recursive action: Splitting the task"
                    + " with startPoint " + startPoint + " and "
                    + " with finishPoint " + finishPoint
                    + " and distance " + distance
                    + ". " + Thread.currentThread().getName());
            List<RecursiveTask> subTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            System.out.println("Recursive action: Doing the task"
                    + " with startPoint " + startPoint + " and "
                    + " with finishPoint " + finishPoint
                    + " and distance " + distance
                    + ". " + Thread.currentThread().getName());
            return IntStream.range(startPoint, finishPoint)
                    .asLongStream()
                    .sum();
        }
    }

    private List<RecursiveTask> createSubTask() {
        List<RecursiveTask> subTasks = new ArrayList<>();
        int middlePoint = (startPoint + finishPoint) / 2;
        RecursiveTask first = new MyTask(startPoint, middlePoint);
        RecursiveTask second = new MyTask(middlePoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
