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
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> recursiveTask : subTasks) {
                recursiveTask.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> recursiveTask : subTasks) {
                result += recursiveTask.join();
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

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        MyTask first = new MyTask(startPoint, startPoint + ((finishPoint - startPoint) / 2));
        MyTask second = new MyTask(startPoint + ((finishPoint - startPoint) / 2), finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
    /*
    100
    0-50,51-100
    0-25,25-50,50-75,75-100
    0-12,12-25, 25-37, 37-50,
    39 - 45
    26 - 30
    76 - 81
    82 - 88
    20 - 23
    31 - 38

     */
}
