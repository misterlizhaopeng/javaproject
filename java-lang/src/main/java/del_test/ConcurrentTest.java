package del_test;

import java.util.concurrent.CountDownLatch;

public class ConcurrentTest {

    /**
     * 线程数量
     */
    public static final int THREAD_NUM = 10000;

    /**
     * 开始时间
     */
    private static long startTime = 0L;

    public void init() {
        try {
            startTime = System.currentTimeMillis();
            System.out.println("CountDownLatch started at: " + startTime);
            // 初始化计数器为1
            CountDownLatch countDownLatch = new CountDownLatch(1);
            for (int i = 0; i < THREAD_NUM; i ++) {
                new Thread(new Run(countDownLatch)).start();
            }
            // 启动多个线程
            countDownLatch.countDown();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    /**
     * 线程类
     */
    private class Run implements Runnable {
        private final CountDownLatch startLatch;
        public Run(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }
        @Override
        public void run() {
            try {
                // 线程等待
                startLatch.await();
                // 执行操作
                System.out.println("当前线程："+Thread.currentThread().getName()+"执行自己的方法");
                long endTime = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + " ended at: " + endTime + ", cost: " + (endTime - startTime) + " ms.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        ConcurrentTest test = new ConcurrentTest();
        test.init();
    }
}