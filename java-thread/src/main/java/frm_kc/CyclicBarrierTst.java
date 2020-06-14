package frm_kc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTst {
    private final static CyclicBarrier cb = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        //ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(() -> {
                try {
                    //Thread.sleep(1000);
                    func(threadNum);
                } catch (Exception e) {
                    System.out.println("exception...," + e);
                }
            });
        }
    }

    private static void func(int threadNum) throws Exception {
        Thread.sleep(1000);
        System.out.println("waiting...,threadNum=" + threadNum);
        //等待cb设置的屏障数量的线程准备就绪后，执行 await 下面的代码
        cb.await();
        System.out.println("is ready ,threadNum" + threadNum);
    }
}
