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
        if (startPoint == finishPoint) {
            return 0L;
        }
        if ((finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubtasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
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

    private List<RecursiveTask<Long>> createSubtasks() {
        List<RecursiveTask<Long>> subtasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint
                + (finishPoint - startPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint
                + (finishPoint - startPoint) / 2), finishPoint);
        subtasks.add(first);
        subtasks.add(second);
        return subtasks;
    }
}
