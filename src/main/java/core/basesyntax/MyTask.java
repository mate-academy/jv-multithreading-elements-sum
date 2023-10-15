package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
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
        if ((finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> list = new ArrayList<>(tasks());
            for (RecursiveTask<Long> task : list) {
                task.fork();
            }
            Long result = 0L;
            for (RecursiveTask<Long> task : list) {
                result += task.join();
            }
            return result;
            // int middle = (startPoint + finishPoint) / 2;
            // MyTask first = new MyTask(startPoint, middle);
            // first.fork();
            // MyTask second = new MyTask(middle, finishPoint);
            // second.fork();
            // return first.join() + second.join();
            // return new MyTask(startPoint, middle).compute() +
            // new MyTask(middle, finishPoint).compute();
        } else {
            long res = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                res += i;
            }
            return res;
        }
    }

    private List<RecursiveTask<Long>> tasks() {
        List<RecursiveTask<Long>> list = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, (startPoint + finishPoint) / 2);
        RecursiveTask<Long> second = new MyTask((startPoint + finishPoint) / 2, finishPoint);
        list.add(first);
        list.add(second);
        return list;
    }
}
