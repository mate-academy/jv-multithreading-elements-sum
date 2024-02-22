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
        if (startPoint > finishPoint) {
            return 0L;
        }
        long res = 0;
        if (Math.abs(finishPoint - startPoint) > 10) {
            ArrayList<RecursiveTask<Long>> recursiveTasks = new ArrayList<>(getSub());
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                recursiveTask.fork();
            }
            for (RecursiveTask<Long> recursiveTask : recursiveTasks) {
                res += recursiveTask.join();
            }
            return res;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                res += i;
            }
        }
        return res;
    }

    private List<RecursiveTask<Long>> getSub() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        int middlePoint = Math.abs(finishPoint - startPoint) / 2;
        subTasks.add(new MyTask(startPoint, startPoint + middlePoint));
        subTasks.add(new MyTask(startPoint + middlePoint, finishPoint));
        return subTasks;
    }
}
