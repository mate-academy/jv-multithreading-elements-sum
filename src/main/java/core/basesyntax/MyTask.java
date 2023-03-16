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
        Long res = 0L;
        if ((finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> tasks = new ArrayList<>(splitTask());
            for (RecursiveTask<Long> task: tasks) {
                task.fork();
            }
            for (RecursiveTask<Long> task: tasks) {
                res += task.join();
            }
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                res += i;
            }
        }
        return res;
    }

    private List<RecursiveTask<Long>> splitTask() {
        List<RecursiveTask<Long>> newList = new ArrayList<>();
        newList.add(new MyTask(startPoint, finishPoint - (finishPoint - startPoint) / 2));
        newList.add(new MyTask(finishPoint - (finishPoint - startPoint) / 2, finishPoint));
        return newList;
    }
}
