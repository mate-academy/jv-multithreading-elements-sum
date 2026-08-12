package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint > 10) {
            List<MyTask> subTasks = new ArrayList<MyTask>(createSubTasks());
            for (MyTask subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (MyTask subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<MyTask> createSubTasks() {
        List<MyTask> subTasks = new ArrayList<>();
        int middle = (startPoint + finishPoint) / 2;

        MyTask first = new MyTask(startPoint, middle);
        MyTask second = new MyTask(middle, finishPoint);

        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
