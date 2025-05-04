package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

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
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subtask: subTasks) {
                subtask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subtask: subTasks) {
                result += subtask.join();
            }
            return result;
        } else {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        RecursiveTask<Long> task1 = new MyTask(startPoint, (finishPoint + startPoint) / 2);
        RecursiveTask<Long> task2 = new MyTask((finishPoint + startPoint) / 2, finishPoint);
        return List.of(task1, task2);
    }
}
