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
            List<RecursiveTask<Long>> subTasks = splitTask();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            int result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return (long) result;
        }
    }

    private List<RecursiveTask<Long>> splitTask() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middle = finishPoint - ((finishPoint - startPoint) / 2);
        subTasks.add(new MyTask(startPoint, middle));
        subTasks.add(new MyTask(middle, finishPoint));
        return subTasks;
    }
}
