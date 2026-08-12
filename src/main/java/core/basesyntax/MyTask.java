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
        // write your code here
        List<RecursiveTask<Long>> subtasks = new ArrayList<>(createSubTasks());
        if (finishPoint - startPoint > 10) {
            System.out.println("RecursiveTask: Splitting the task with point difference: "
                    + (finishPoint - startPoint) + ". " + Thread.currentThread().getName());
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subtask : subtasks) {
                subtask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subtask : subtasks) {
                result += subtask.join();
            }
            return result;
        } else {
            return (long) IntStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middle = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, middle);
        RecursiveTask<Long> second = new MyTask(middle, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;

    }
}
