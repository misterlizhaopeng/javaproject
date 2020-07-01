package tl.executors;

import org.junit.Test;

import java.util.concurrent.*;


// 自定义线程池：
public class SelfDefiniThreadPoolIns {

    private static final int COUNT_BITS = Integer.SIZE - 3;//29
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;//536870911

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;




    public static void main(String[] args) {
        System.out.println(COUNT_BITS);
        System.out.println("CAPACITY:"+CAPACITY);
        System.out.println("RUNNING:"+RUNNING);
        System.out.println("SHUTDOWN:"+SHUTDOWN);
        System.out.println("STOP:"+STOP);
        System.out.println("TIDYING:"+TIDYING);
        System.out.println("TERMINATED:"+TERMINATED);


    }

    @Test
    public void test01() {
        //有界队列
        BlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(20);
        ThreadPoolExecutor poolExecutor =
                new ThreadPoolExecutor(3, 5, 50, TimeUnit.SECONDS, arrayBlockingQueue);

        //通过线程池，启动八个任务，查看多少线程池去执行
        for (int i = 0; i < 8; i++) {
            poolExecutor.execute(() -> {
                System.out.println("current thread name is :" + Thread.currentThread().getName());
            });
        }
        //关闭线程池
        poolExecutor.shutdown();

    }


    static int count;

    @Test
    public void test02() throws Exception {
        ExecutorService poolExecutor = Executors.newFixedThreadPool(5);
        //通过线程池，启动八个任务，查看多少线程池去执行

        CountDownLatch countDownLatch = new CountDownLatch(800);
        for (int i = 0; i < 800; i++) {
            poolExecutor.execute(() -> {
                count++;
                System.out.println("current thread name is :" + Thread.currentThread().getName());
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("count:" + count);
        //关闭线程池
        poolExecutor.shutdown();

    }
}