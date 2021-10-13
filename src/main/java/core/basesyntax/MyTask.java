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
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            if (startPoint >= finishPoint) {
                return 0L;
            }
            if (startPoint == finishPoint - 1) {
                return (long) startPoint;
            }
            MyTask myTask = new MyTask(startPoint + 1, finishPoint);
            return myTask.compute() + startPoint;
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = (startPoint + finishPoint) / 2;
        RecursiveTask<Long> firstTask = new MyTask(startPoint, middlePoint);
        RecursiveTask<Long> secondTask = new MyTask(middlePoint, finishPoint);
        subTasks.add(firstTask);
        subTasks.add(secondTask);
        return subTasks;
    }
}
