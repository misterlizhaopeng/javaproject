package tl.sy_aqs_2_1;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//屏障处的回调方法-await等待的所有线程完成之后，首先执行此回调方法，然后在执行await之后的方法！
public class CyclicBarrier03 {

    final static CyclicBarrier cyclicBarrier = new CyclicBarrier(5,()->{
        System.out.println("await等待的所有线程完成之后，首先执行此方法，然后在执行await之后的方法！");
    });

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
        // 表示等待指定许可个数的线程都就绪后，再一起执行await()后面的逻辑
        cyclicBarrier.await();
        System.out.println("continue...," + Thread.currentThread().getName());
    }
}
