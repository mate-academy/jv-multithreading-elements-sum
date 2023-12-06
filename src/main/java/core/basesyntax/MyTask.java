package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

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
            System.out.println("Splitting the task with start point - "
                    + startPoint + " and finish point - " + finishPoint);
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            System.out.println("Doing the task myself start point - "
                    + startPoint + " and finish point - " + finishPoint);
            return LongStream.range(startPoint, finishPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middle = startPoint + finishPoint;
        RecursiveTask<Long> first = new MyTask(startPoint, middle / 2);
        RecursiveTask<Long> second = new MyTask(middle / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
