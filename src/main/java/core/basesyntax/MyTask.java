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
        if (finishPoint - startPoint <= 10) {
            long sum = 0L;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }

        int middlePoint = startPoint + (finishPoint - startPoint) / 2;

        MyTask myFirstTask = new MyTask(startPoint, middlePoint);
        MyTask mySecondTask = new MyTask(middlePoint, finishPoint);

        myFirstTask.fork();
        mySecondTask.fork();

        Long first = myFirstTask.join();
        Long second = mySecondTask.join();

        return first + second;
    }
}
