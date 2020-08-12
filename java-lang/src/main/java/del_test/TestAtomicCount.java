package del_test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

public class TestAtomicCount {
    //请求个数
    private static Integer req = 2000;
    // 线程数
    private static Integer thNum = 5;
    // 临界资源
    private static AtomicLong atomicLong = new AtomicLong();
    // private static   Integer count = 0;

    public static void main(String[] args) {
        // 搞一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        Semaphore semaphore = new Semaphore(thNum);
        CountDownLatch countDownLatch = new CountDownLatch(req);
        for (int i = 0; i < req; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    count();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
            //为什么计数放到这里，就不能完整计数呢 ？
            // 因为要针对每一个线程计数才可以，这里不是线程里面，需要放到线程里面
            //countDownLatch.countDown();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("count:" + count);
        System.out.println("count:" + atomicLong.get());
        executorService.shutdown();
    }

    public static void count() {
//        count++;
        atomicLong.incrementAndGet();
    }
}
