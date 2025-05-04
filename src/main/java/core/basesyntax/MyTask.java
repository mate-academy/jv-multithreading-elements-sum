package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int WORK_LOAD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int range = finishPoint - startPoint;
        if (range > WORK_LOAD) {
            System.out.println("RecursiveTask: Splitting task, workLoad : " + range);
            List<RecursiveTask<Long>> taskList = new ArrayList<>(createSubTask());
            for (RecursiveTask<Long> task : taskList) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> task : taskList) {
                result += task.join();
            }
            return result;
        } else {
            System.out.println("RecursiveTask: Doing task myself, workLoad : " + WORK_LOAD);
            return compoteDirectly();
        }
    }

    private List<RecursiveTask<Long>> createSubTask() {
        List<RecursiveTask<Long>> taskList = new ArrayList<>();
        int midPoint = startPoint + (finishPoint - startPoint) / 2;
        taskList.add(new MyTask(startPoint, midPoint));
        taskList.add(new MyTask(midPoint, finishPoint));
        return taskList;
    }

    private long compoteDirectly() {
        long sum = 0;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
