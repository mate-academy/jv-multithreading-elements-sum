package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
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
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            subTasks.forEach(ForkJoinTask::fork);
            long res = 0;
            for (RecursiveTask<Long> subTask : subTasks) {
                res += subTask.join();
            }
            return res;
        } else {
            long res = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                res += i;
            }
            return res;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> newTasks = new ArrayList<>();
        int middle = finishPoint - (finishPoint - startPoint) / 2;
        RecursiveTask<Long> first = new MyTask(startPoint, middle);
        RecursiveTask<Long> second = new MyTask(middle, finishPoint);
        newTasks.add(first);
        newTasks.add(second);
        return newTasks;
    }
}
