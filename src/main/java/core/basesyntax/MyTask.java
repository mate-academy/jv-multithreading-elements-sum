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
            System.out.println(String.format("Recursive task: splitting the task with "
                    + "%d and %d. %s ",
                    startPoint,
                    finishPoint,
                    Thread.currentThread().getName()));
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0L;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            System.out.println(String.format("Recursive task: performing the task myself: "
                    + "%d and %d. %s ",
                    startPoint,
                    finishPoint,
                    Thread.currentThread().getName()));
            Long result = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int half = finishPoint / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
