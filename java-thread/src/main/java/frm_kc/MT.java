package frm_kc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 测试单例的懒汉模式-线程安全性
public class MT {
    public static void main(String[] args) throws Exception {
        Set<TestSingleton_lh> set = new HashSet<>();
        List<TestSingleton_lh> list = new ArrayList<>();
        int count = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                System.out.println("thread name-------------->" + Thread.currentThread().getName());
                TestSingleton_lh instance = TestSingleton_lh.getInstance();
                set.add(instance);
                if (!list.contains(instance))
                    list.add(instance);
            });
            countDownLatch.countDown();
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("set count=" + set.size());
        System.out.println("list count=" + list.size());
    }
}
