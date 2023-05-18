package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTask extends RecursiveTask<Long> {
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int result = 0;
        if ((finishPoint - startPoint) > 10) {
            System.out.println("Splitting the task");
            List<MyTask> subTasks = new ArrayList<>(createSubTask());
            for (MyTask subTask : subTasks) {
                subTask.fork();
            }
            long res = 0;
            for (MyTask subTask : subTasks) {
                res += subTask.join();
            }
            return res;
        } else {
            System.out.println("Doing the task herself");
            int[] arr = IntStream.range(startPoint, finishPoint).toArray();
            result = IntStream.of(arr).sum();
        }
        return Long.valueOf(result);
    }

    private List<MyTask> createSubTask() {
        List<MyTask> subTasks = new ArrayList<>();
        MyTask first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        MyTask second = new MyTask((finishPoint + startPoint) / 2, finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }
}
