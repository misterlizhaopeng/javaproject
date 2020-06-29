package tl.sy_aqs_2_1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 主线程只等待子线程指定的时间，之后再继续执行
public class CountDownLatchTest02 {

    //模拟200个请求
    private final static Integer threadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //创建一个计数器
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    System.out.println("exception: " + e.toString());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        // 主线程只等待子线程10毫秒，10毫秒后执行主线程，不影响子线程的执行情况
        // 这种情况：可以避免子线程执行时间过长，导致一直等待的情况
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        System.out.println("finished");
        executorService.shutdown();

    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        System.out.println("threadNum:" + threadNum);
    }


}
