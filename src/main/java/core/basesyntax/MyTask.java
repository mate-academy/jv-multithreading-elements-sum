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
            System.out.println("RecursiveTask: Splitting task, startPoint : " + startPoint
                    + ", finishPoint : " + finishPoint);
            List<RecursiveTask<Long>> subTasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> subTask: subTasks) {
                subTask.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> subTask: subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            System.out.println("RecursiveTask: Doing task myself, startPoint : " + startPoint
                    + ", finishPoint : " + finishPoint);
            long result = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> taskList = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (finishPoint + startPoint) / 2);
        RecursiveTask<Long> second = new MyTask((finishPoint + startPoint) / 2, finishPoint);
        taskList.add(first);
        taskList.add(second);
        return taskList;
    }
}
