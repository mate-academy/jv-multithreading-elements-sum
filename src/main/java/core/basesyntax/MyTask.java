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
        long result = 0;
        if (finishPoint - startPoint <= 10) {
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask first = new MyTask(startPoint, middle);
            MyTask second = new MyTask(middle, finishPoint);
            first.fork();
            second.fork();
            result = first.join() + second.join();
        }
        return result;
    }
}
