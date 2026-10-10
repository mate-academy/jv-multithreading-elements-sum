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
        int distance = finishPoint - startPoint;
        long counter = 0;
        if (distance > 10) {
            int midle = (finishPoint + startPoint) / 2;
            MyTask myTask1 = new MyTask(startPoint, midle);
            myTask1.fork();
            MyTask myTask2 = new MyTask(midle, finishPoint);
            myTask2.fork();
            return myTask1.join() + myTask2.join();

        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                counter += i;
            }
        }
        return counter;
    }
}
