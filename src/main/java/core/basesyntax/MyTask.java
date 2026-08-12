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
        long result = 0;
        if (finishPoint - startPoint > 10) {
            List<MyTask> subTasks = createSubTasks();
            for (MyTask subTask : subTasks) {
                subTask.fork();
            }
            for (MyTask subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<MyTask> createSubTasks() {
        List<MyTask> subTasks = new ArrayList<>();
        int steps = finishPoint - startPoint;
        int subTaskSize = steps / 2;
        int subTaskStart = startPoint;
        while (steps > 0) {
            int subTaskEnd = subTaskStart + Math.min(subTaskSize, steps);
            subTasks.add(new MyTask(subTaskStart, subTaskEnd));
            subTaskStart = subTaskEnd;
            steps -= subTaskSize;
        }
        return subTasks;
    }
}
