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
        int distance = finishPoint - startPoint;
        if (distance <= 10) {
            long sum = 0;
            for (long i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            System.out.println("Sum from " + startPoint + " to " + finishPoint + " is " + sum);
            return sum;
        } else {

            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int distance = finishPoint - startPoint;
        int mid = startPoint + distance / 2;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, mid);
        RecursiveTask<Long> second = new MyTask(mid, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
