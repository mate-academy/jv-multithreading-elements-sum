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
        long summa = 0L;
        if (finishPoint - startPoint > 10) {
            MyTask myTask1 = new MyTask(startPoint, (startPoint + finishPoint) / 2);
            MyTask myTask2 = new MyTask((startPoint + finishPoint) / 2, finishPoint);
            myTask1.fork();
            myTask2.fork();
            summa += myTask1.join() + myTask2.join();
        } else {
            if (startPoint < finishPoint) {
                for (int i = startPoint; i < finishPoint; i++) {
                    summa += i;
                }
            }
        }
        return summa;
    }
}
