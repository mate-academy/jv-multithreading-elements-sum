package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

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
        if (finishPoint - startPoint > 10) {
            List<RecursiveTask<Long>> taskList = new ArrayList(createSubTusks());
            for (RecursiveTask t : taskList) {
                t.fork();
            }
            for (RecursiveTask<Long> t : taskList) {
                result += t.join();
            }
        } else {
            result = LongStream.range(startPoint,finishPoint).sum();
        }
        return result;
    }

    private List<RecursiveTask<Long>> createSubTusks() {
        List<RecursiveTask<Long>> list = new ArrayList();
        int middle = (startPoint + finishPoint) / 2;
        list.add(new MyTask(startPoint, middle));
        list.add(new MyTask(middle, finishPoint));
        return list;
    }
}
