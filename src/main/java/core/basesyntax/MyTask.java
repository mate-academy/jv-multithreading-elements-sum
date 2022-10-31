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
        Long result = 0L;
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> tasks = getTasks();
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task : tasks) {
                result += task.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }

    private List<RecursiveTask<Long>> getTasks() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        int middle = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> taskOne = new MyTask(startPoint, middle);
        RecursiveTask<Long> taskTwo = new MyTask(middle, finishPoint);
        tasks.add(taskOne);
        tasks.add(taskTwo);
        return tasks;
    }
}
