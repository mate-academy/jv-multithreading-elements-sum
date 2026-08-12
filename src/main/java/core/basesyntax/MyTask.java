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
        if (finishPoint - startPoint > 10) {
            System.out.println(
                    String.format("RecursiveTask: Splitting task, startpoint %d, finishpoint %s ",
                            startPoint, finishPoint));
            List<RecursiveTask<Long>> subtasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subtask : subtasks) {
                subtask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> subtask : subtasks) {
                result += subtask.join();
            }
            return result;
        } else {
            System.out.println(String.format("Normal task: Start: %d, Finish: %d",
                    startPoint, finishPoint));
            return (long) IntStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subtasks = new ArrayList<>();
        int middlePoint = finishPoint - (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> second = new MyTask(middlePoint, finishPoint);
        subtasks.add(first);
        subtasks.add(second);
        return subtasks;
    }
}
