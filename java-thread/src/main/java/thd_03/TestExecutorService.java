package thd_03;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestExecutorService {
    public static void main(String[] args) throws InterruptedException{
        int i=1000;
        CountDownLatch cou =new CountDownLatch(i);
        //创建固定的个数的线程
        ExecutorService executorService = Executors.newFixedThreadPool(i);
        for (int j = 0; j < i; j++) {
            executorService.execute(()->{
                System.out.println("当前线程的名称："+Thread.currentThread().getName());
                cou.countDown();
            });
        }
        cou.await();
        System.out.println("线程池里面的线程都已经执行完毕，当前线程为主线程，线程名称："+Thread.currentThread().getName());
    }
}
