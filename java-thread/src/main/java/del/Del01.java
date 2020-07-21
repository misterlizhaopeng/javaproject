package del;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Del01 {
    public static void main(String[] args) throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(100);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {

            try {

                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            } finally {
                countDownLatch.countDown();
            }
            countDownLatch.await();
        }
    }
}
