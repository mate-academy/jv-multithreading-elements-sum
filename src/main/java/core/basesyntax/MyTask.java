package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        Long result = 0L;
        int distance = finishPoint - startPoint;
        if (distance > THRESHOLD) {
            List<MyTask> subTasks = new ArrayList<>();
            int middle = (startPoint + finishPoint) / 2;
            MyTask leftSubtask = new MyTask(startPoint, middle);
            MyTask rightSubtask = new MyTask(middle, finishPoint);
            subTasks.add(leftSubtask);
            subTasks.add(rightSubtask);
            for (MyTask subTask : subTasks) {
                subTask.fork();
            }
            for (MyTask subTask : subTasks) {
                result += subTask.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        }
        return result;
    }
}
