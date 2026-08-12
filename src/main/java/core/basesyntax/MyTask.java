package core.basesyntax;

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
            List<MyTask> tasks = createSubTasks();
            for (MyTask task : tasks) {
                task.fork();
            }
            long result = 0;
            for (MyTask task : tasks) {
                result += task.join();
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

    private List<MyTask> createSubTasks() {
        int mid = (startPoint + finishPoint) / 2;
        MyTask left = new MyTask(startPoint, mid);
        MyTask right = new MyTask(mid, finishPoint);
        return List.of(left, right);
    }
}
