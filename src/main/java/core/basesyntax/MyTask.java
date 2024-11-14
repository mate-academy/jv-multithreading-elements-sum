package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final Integer THRESHOLD = 10;
    private int startPoint;
    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        // write your code here
        Long answer = 0L;
        if (finishPoint - startPoint <= THRESHOLD) {
            for (int i = startPoint; i < finishPoint; i++) {
                answer += i;
            }
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            RecursiveTask<Long> first = new MyTask(startPoint, middle);
            RecursiveTask<Long> second = new MyTask(middle, finishPoint);
            first.fork();
            second.fork();
            answer += first.join();
            answer += second.join();
        }
        return answer;
    }
}
