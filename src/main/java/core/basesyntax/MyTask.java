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
        int distance = finishPoint - startPoint;
        if (distance > 10) {
            int mid = startPoint + distance / 2;
            List<RecursiveTask<Long>> subTasks = new ArrayList<>();
            RecursiveTask<Long> first = new MyTask(startPoint, mid);
            RecursiveTask<Long> second = new MyTask(mid, finishPoint);
            subTasks.add(first);
            subTasks.add(second);
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
