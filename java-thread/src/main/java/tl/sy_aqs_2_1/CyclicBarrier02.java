package tl.sy_aqs_2_1;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//表示等待指定的时间（此处是等待2s），便可以执行后续的任务，注意捕捉异常
public class CyclicBarrier02 {

    final static CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            // 每隔1s启动一个线程
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    calc(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    public static void calc(int threadNum) throws Exception {
        // 模拟业务耗时
        Thread.sleep(1000);
        System.out.println("threadNum:" + threadNum + "," + Thread.currentThread().getName());
        // 表示等待指定的时间（此处是等待2s），便可以执行后续的任务，注意捕捉异常
        try {
            cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("continue...," + Thread.currentThread().getName());
    }
}
