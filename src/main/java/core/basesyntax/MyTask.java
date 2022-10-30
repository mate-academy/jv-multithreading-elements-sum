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
        if ((finishPoint - startPoint) <= 1) {
            return 0L;
        }
        if ((finishPoint - startPoint) >= 10) {
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubtask());
            for (RecursiveTask<Long> subTask: subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> subTask: subTasks) {
                result += subTask.join();
            }
            if (Thread.currentThread().getName().equals("main")) {
                result = result - finishPoint;
            }
            return result;
        } else {
            Long result = 0L;
            int ptr = startPoint;
            while (ptr <= finishPoint) {
                result += ptr;
                ptr++;
            }
            if (Thread.currentThread().getName().equals("main")) {
                result = result - finishPoint;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubtask() {
        int start = this.startPoint;
        int end = this.finishPoint;
        int medium = start + (end - start) / 2;
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(start, medium);
        RecursiveTask<Long> second = new MyTask(medium + 1, end);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
