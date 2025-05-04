package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int DIFFERENCE = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (startPoint >= finishPoint) {
            return 0L;
        }
        if (Math.abs(finishPoint) - Math.abs(startPoint) > DIFFERENCE) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask: subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask: subTasks) {
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

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + DIFFERENCE);
        RecursiveTask<Long> second = new MyTask(startPoint + DIFFERENCE, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
