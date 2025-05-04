package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private static final int DISTANCE_THRESHOLD = 10;
    private static final int SUBTASK_QUANTITY = 4;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > DISTANCE_THRESHOLD) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(crateSubTask());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;

        } else {
            int sum = IntStream.range(startPoint, finishPoint).sum();
            return (long)sum;
        }
    }

    private List<RecursiveTask<Long>> crateSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int range = finishPoint - startPoint;
        int chunkSize = range / SUBTASK_QUANTITY;
        for (int i = 0; i < SUBTASK_QUANTITY; i++) {
            int subStart = startPoint + i * chunkSize;
            int subEnd = subStart + chunkSize;
            if (i == SUBTASK_QUANTITY - 1) {
                subEnd = finishPoint;
            }
            subTasks.add(new MyTask(subStart, subEnd));
        }
        return subTasks;
    }
}
