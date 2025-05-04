package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;
    private Long result = 0L;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint - 1 > THRESHOLD) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }

            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }

            System.out.println("split " + Thread.currentThread().getName());
            return result;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            System.out.println("sum = " + result);
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int midl = (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, startPoint + midl);
        RecursiveTask<Long> second = new MyTask(startPoint + midl, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
