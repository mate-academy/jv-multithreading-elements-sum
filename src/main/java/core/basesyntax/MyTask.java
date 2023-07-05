package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int DIFFERENCE = 10;
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
        if (Math.abs(finishPoint) - Math.abs(startPoint) > DIFFERENCE) {
            List<RecursiveTask<Long>> subtasks = new ArrayList<>(createSubtasks());
            for (RecursiveTask<Long> subtask : subtasks) {
                subtask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subtask : subtasks) {
                result += subtask.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubtasks() {
        List<RecursiveTask<Long>> subtask = new ArrayList<>();
        int distance = finishPoint - startPoint;
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + distance / 2);
        RecursiveTask<Long> second = new MyTask(startPoint + distance / 2, finishPoint);
        subtask.add(first);
        subtask.add(second);
        return subtask;
    }
}
