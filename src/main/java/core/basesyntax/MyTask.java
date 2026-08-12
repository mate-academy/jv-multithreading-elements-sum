package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (finishPoint - startPoint > THRESHOLD) {
            List<MyTask> subTasks = split();
            for (MyTask subTask : subTasks) {
                subTask.fork();
            }
            for (MyTask subTask : subTasks) {
                result += subTask.join();
            }
            return result;
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<MyTask> split() {
        int median = startPoint + (finishPoint - startPoint) / 2;
        MyTask myTask1 = new MyTask(startPoint, median);
        MyTask myTask2 = new MyTask(median, finishPoint);
        List<MyTask> myTaskList = new ArrayList<>();
        myTaskList.add(myTask1);
        myTaskList.add(myTask2);
        return myTaskList;
    }
}
