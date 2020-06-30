package tl.atomic_;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// frm-InitAtomicTest
// 通过 AtomicLong  原子类实现临界资源累加的安全操作：
public class InitAtomicTest_atomicLong {

    //临界资源
    //private static int count = 0;
    private static AtomicLong count=new AtomicLong();
    //请求个数
    private static int reqNum = 20000;
    //处理请求的线程数
    private static int threadNum = 200;

    public static void main(String[] args) throws Exception {
        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量，表示一次性允许多少线程同时操作业务（并发操作）
        // 引入目的：限制并发数量
        Semaphore semaphore = new Semaphore(threadNum);
        //作用为计数，当前共有2000个请求，计数全部处理完毕，在执行主线程的业务
        // 引入目的：等待所有线程完成任务，最后统计临界资源的值
        CountDownLatch countDownLatch = new CountDownLatch(reqNum);
        for (int i = 0; i < reqNum; i++) {
            //把请求全部放入到线程池，进行处理，executorService.execute 表示开启一个线程执行任务
            executorService.execute(() -> {
                try {
                    //这里表示：当前线程获取了许可，便可以执行业务代码，许可数量不允许，那么当前线程会等待别的线程释放许可
                    semaphore.acquire();
                    count();
                    semaphore.release();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("count=" + count.get());
        executorService.shutdown();
    }

    public static void count() {
        count.incrementAndGet();
    }
}
