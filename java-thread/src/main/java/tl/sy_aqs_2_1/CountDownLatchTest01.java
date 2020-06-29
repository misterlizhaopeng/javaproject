package tl.sy_aqs_2_1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//等待所有的线程都执行完毕之后，再执行主线程
public class CountDownLatchTest01 {

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
        countDownLatch.await();//等待所有的线程都执行完毕之后，再执行主线程
        System.out.println("finished");
        executorService.shutdown();

    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        System.out.println("threadNum:" + threadNum);
    }


}
