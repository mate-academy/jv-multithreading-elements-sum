package core.basesyntax;

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
        // write your code here
        long res = 0;
        int diff = finishPoint - startPoint;
        if (diff > 10) {
            System.out.println("Start: " + finishPoint + " Finish: " + finishPoint);
            MyTask myFirstTask = new MyTask(startPoint, startPoint + diff / 2);
            MyTask mySecondTask = new MyTask(startPoint + diff / 2, finishPoint);
            myFirstTask.fork();
            res += myFirstTask.join();
            mySecondTask.fork();
            res += mySecondTask.join();
        } else {
            for (int i = startPoint; i < finishPoint; ++i) {
                res += i;
            }
        }
        return res;
    }
}
