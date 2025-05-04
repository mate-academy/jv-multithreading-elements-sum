package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;
    private Long workLoad = (finishPoint - startPoint) * 1L;
    private Long result = 0L;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    public Long getResult() {
        return result;
    }

    @Override
    protected Long compute() {
        if (workLoad > 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }

            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }

            return result;
            
        } else {
            Thread.currentThread().getName();
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask first
                = new MyTask((finishPoint - startPoint) / 2, (finishPoint - startPoint));
        RecursiveTask second
                = new MyTask((finishPoint - startPoint) / 2, (finishPoint - startPoint));
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
