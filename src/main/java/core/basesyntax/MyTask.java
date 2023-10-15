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
        // write your code here
        long sum = 0L;
        int workload = finishPoint - startPoint;
        if (workload <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            List<RecursiveTask<Long>> subTasks = createSubTask();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                sum += subTask.join();
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();

        int middle = (startPoint + finishPoint) / 2;
        subTasks.add(new MyTask(startPoint, middle));
        subTasks.add(new MyTask(middle, finishPoint));

        return subTasks;
    }
}
