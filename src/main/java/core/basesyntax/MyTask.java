package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final Integer TASK_LOAD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0L;
        if (finishPoint - startPoint > TASK_LOAD) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                recursiveTask.fork();
            }
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                result += recursiveTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>();
        int difference = finishPoint - startPoint;
        RecursiveTask<Long> first = new MyTask(startPoint, finishPoint - difference / 2);
        RecursiveTask<Long> second = new MyTask(finishPoint - difference / 2, finishPoint);
        recursiveTasks.add(first);
        recursiveTasks.add(second);
        return recursiveTasks;
    }
}
