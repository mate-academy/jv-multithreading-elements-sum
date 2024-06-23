package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int MAX_DIFFERENCE = 10;

    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int workLoad = finishPoint - startPoint;

        if (workLoad > MAX_DIFFERENCE) {
            List<MyTask> subTasks = createSubTasks();

            for (MyTask subTask : subTasks) {
                subTask.fork();
            }

            long result = 0;
            for (MyTask subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            return sum(startPoint, finishPoint);
        }
    }

    private List<MyTask> createSubTasks() {
        List<MyTask> subTasks = new ArrayList<>();
        int middle = startPoint + (finishPoint - startPoint) / 2;

        MyTask first = new MyTask(startPoint, middle);
        MyTask second = new MyTask(middle, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }

    private long sum(int start, int end) {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += i;
        }
        return sum;
    }
}
