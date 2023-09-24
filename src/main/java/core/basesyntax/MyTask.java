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
        System.out.println("finish point - " + finishPoint + " start point - " + startPoint);
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            long temp = (long) ((finishPoint - startPoint)/2) * (startPoint + finishPoint - 1);
            System.out.println("doing work - " + temp + " computation - " + (startPoint + finishPoint - 1));
            return temp;
        }
    }

    private List<RecursiveTask> createSubTasks() {
        List<RecursiveTask> subTasks = new ArrayList<>();
        RecursiveTask first = new MyTask(startPoint / 2, finishPoint / 2);
        RecursiveTask second = new MyTask(startPoint / 2, finishPoint / 2);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
