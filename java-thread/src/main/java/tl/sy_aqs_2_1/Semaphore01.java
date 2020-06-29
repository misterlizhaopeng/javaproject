package tl.sy_aqs_2_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semaphore01 {
    // 模拟 20个请求，一次处理5个
    //20个请求
    private final static Integer threadCount = 20;

    public static void main(String[] args) {

        //利用线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();

        //一次可以接受5个线程执行，执行完毕再接受5个执行
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            threadPool.execute(() -> {
                try {
                    // 每一秒执行5个线程
                    semaphore.acquire();// 获取一个许可
                    test(threadNum);
                    semaphore.release();// 释放一个许可
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }


    public static void test(Integer threadNum) throws Exception {
        System.out.println("threadNum:" + threadNum);
         Thread.sleep(1000);
    }
}
