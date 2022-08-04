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
        long output = 0;
        if (startPoint - finishPoint < 10) {
            return LongStream.range(startPoint, finishPoint)
                    .sum();
        }
        java.util.List<RecursiveTask<Long>> subtasks = new ArrayList<>(getSubtask());
        for (RecursiveTask<Long> subtask : subtasks) {
            subtask.fork();
        }

        for (RecursiveTask<Long> subtask : subtasks) {
            output += subtask.join();
        }

        return output;
    }

    private List<RecursiveTask<Long>> getSubtask() {
        List<RecursiveTask<Long>> subtasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        subtasks.add(first);
        subtasks.add(second);
        return subtasks;
    }
}
