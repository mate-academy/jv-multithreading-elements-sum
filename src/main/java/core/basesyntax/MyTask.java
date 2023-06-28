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
            List<RecursiveTask<Long>> tasks = split();
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> task : tasks) {
                result += task.join();
            }
            return result;
        } else {
            long sum = 0;
            for (int i = startPoint ; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> split() {
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (finishPoint + startPoint) / 2);
        RecursiveTask<Long> second = new MyTask((finishPoint + startPoint) / 2 , finishPoint);
        tasks.add(first);
        tasks.add(second);
        return tasks;
    }
}
