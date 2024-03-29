package frm_kc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomic {
    private static int threadTotal = 200;
    private static int clientTotal = 5000;

    //AtomicInteger 原理就是CAS
    private static AtomicInteger  count = new AtomicInteger();


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    // 看下当前线程是否允许被执行，如果达到一定的并发数之后，下面的func函数可能被阻塞掉；acquire能返回出来值之后，下面的fun才会被执行；
                    semaphore.acquire();
                    fun();
                    //释放当前线程
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        //关闭线程池
        executorService.shutdown();
        //等待其他现场执行完毕，在往下执行
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count=" + count.get());
    }

    public static void fun() {
        //count++;
        count.incrementAndGet();
    }
}
