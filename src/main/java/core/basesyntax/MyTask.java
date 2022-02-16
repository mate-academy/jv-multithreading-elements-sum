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
    public Long compute() {
        long result = 0;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(getRecursiveTasks());
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

    private List<RecursiveTask<Long>> getRecursiveTasks() {
        List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint,
                (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2,
                finishPoint);
        recursiveTasks.add(first);
        recursiveTasks.add(second);
        return recursiveTasks;
    }
}
