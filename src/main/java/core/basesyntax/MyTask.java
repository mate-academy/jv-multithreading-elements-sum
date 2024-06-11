package core.basesyntax;

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
        long result = 0;
        if (finishPoint - startPoint <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        } else {
            int middlePoint = (startPoint + finishPoint) / 2;
            MyTask firstHalf = new MyTask(startPoint, middlePoint);
            MyTask secondHalf = new MyTask(middlePoint, finishPoint);
            firstHalf.fork();
            secondHalf.fork();
            result = firstHalf.join() + secondHalf.join();
        }
        return result;
    }
}
