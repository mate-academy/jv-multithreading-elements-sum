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
        int workLoad = finishPoint - startPoint;
        if (workLoad > 10) {
            System.out.println("RecursiveTask: Splitting task, workLoad : " + workLoad);
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            System.out.println("RecursiveTask: Doing task myself, workLoad : " + workLoad);
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int midPoint = (startPoint + finishPoint) / 2;

        RecursiveTask<Long> first = new MyTask(startPoint, midPoint);
        RecursiveTask<Long> second = new MyTask(midPoint, finishPoint);

        subTasks.add(first);
        subTasks.add(second);

        return subTasks;
    }
}
