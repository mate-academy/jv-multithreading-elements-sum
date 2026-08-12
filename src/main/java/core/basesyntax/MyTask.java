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
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        } else {
            int middle = (startPoint + finishPoint) / 2;
            MyTask myTask1 = new MyTask(startPoint, middle);
            MyTask myTask2 = new MyTask(middle, finishPoint);

            myTask1.fork();
            long result2 = myTask2.compute();
            long result1 = myTask1.join();

            long result = result1 + result2;

            return result;
        }
    }
}
