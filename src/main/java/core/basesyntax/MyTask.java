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
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            List<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> task : recursiveTasks) {
                task.fork();
                result += task.join();
            }
        } else {
            int value = startPoint;
            int counter = distance;
            while (counter > 0) {
                result += value++;
                counter--;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int nextStartPoint = startPoint + 9;
        RecursiveTask<Long> first = new MyTask(startPoint, nextStartPoint);
        RecursiveTask<Long> second = new MyTask(nextStartPoint, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
