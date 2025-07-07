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
        int sum = 0;
        if (finishPoint - startPoint < 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
        } else {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            for (RecursiveTask<Long> subTask : subTasks) {
                sum += subTask.join();
            }

        }
        return Long.valueOf(sum);
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middle = (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + middle));
        RecursiveTask<Long> second = new MyTask((startPoint + middle), finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
