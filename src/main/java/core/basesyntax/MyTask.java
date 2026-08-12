package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    public static final int MAX = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        int length = finishPoint - startPoint; // Это и есть ваш workLoad
        if (length <= MAX) {
            long sum = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                sum += i;
            }
            return sum;
        }
        int middle = startPoint + (finishPoint - startPoint) / 2;
        MyTask leftTask = new MyTask(startPoint, middle);
        MyTask rightTask = new MyTask(middle, finishPoint);
        leftTask.fork(); // Запускаем одну задачу асинхронно
        return rightTask.compute() + leftTask.join();
    }
}
