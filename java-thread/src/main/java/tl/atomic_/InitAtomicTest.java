package tl.atomic_;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//原子操作-初始化的例子（对临界资源count的不安全操作）：当前例子意图：通过设置线程池处理请求任务，通过semaphore信号量控制并发最大线程数，
// 通过 countDownLatch 进行计数，控制所有请求任务执行完毕，再执行主线程，子线程进行对临界资源进行累加操作；
// 当前是非安全操作：
public class InitAtomicTest {

    //临界资源
    private static int count = 0;
    //请求个数
    private static int reqNum = 2000;
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
        System.out.println("count=" + count);
        executorService.shutdown();
    }

    public static void count() {
        count++;
    }
}
