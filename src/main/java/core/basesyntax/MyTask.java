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
        long result = 0L;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : recursiveTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> task : recursiveTasks) {
                result += task.join();
            }
        } else {
            for (long i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        MyTask taskOne = new MyTask(startPoint, (finishPoint + startPoint) / 2);
        MyTask taskTwo = new MyTask((finishPoint + startPoint) / 2, finishPoint);
        subTasks.add(taskOne);
        subTasks.add(taskTwo);
        return subTasks;
    }
}
