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
        if (finishPoint - startPoint <= 10) {
            int result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return (long) result;
        }

        int middle = (startPoint + finishPoint) / 2;

        MyTask leftTask = new MyTask(startPoint, middle);
        MyTask rightTask = new MyTask(middle, finishPoint);
        leftTask.fork();
        rightTask.fork();
        return leftTask.join() + rightTask.join();
    }
}
