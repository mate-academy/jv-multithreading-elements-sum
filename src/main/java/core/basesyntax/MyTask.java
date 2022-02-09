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
        if (finishPoint > 0 && startPoint > 0 && (finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(getRecursiveTasks());
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                recursiveTask.fork();
            }
        }
        if (finishPoint < 0 && startPoint < 0 && (finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> recursiveTasks =
                    new ArrayList<>(getRecursiveTasksNegativeNumbers());
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                recursiveTask.fork();
            }
        }
        if (finishPoint > 0 && startPoint < 0 && (finishPoint + startPoint) > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(getRecursiveTasks());
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                recursiveTask.fork();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> getRecursiveTasksNegativeNumbers() {
        List<RecursiveTask<Long>> recursiveTasks1 = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint + 10, finishPoint + 10);
        RecursiveTask<Long> second = new MyTask(startPoint + 10, finishPoint + 10);
        recursiveTasks1.add(first);
        recursiveTasks1.add(second);
        return recursiveTasks1;
    }

    private List<RecursiveTask<Long>> getRecursiveTasks() {
        List<RecursiveTask<Long>> recursiveTasks2 = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint + 10, finishPoint - 10);
        RecursiveTask<Long> second = new MyTask(startPoint + 10, finishPoint - 10);
        recursiveTasks2.add(first);
        recursiveTasks2.add(second);
        return recursiveTasks2;
    }
}
