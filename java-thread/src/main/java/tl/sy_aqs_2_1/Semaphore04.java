package tl.sy_aqs_2_1;

import java.util.concurrent.Semaphore;

// 尝试获取一个许可，看许可的个数，获取指定的许可完毕，剩下的没有获取许可的线程放弃执行权限
public class Semaphore04 {
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    // 尝试获取一个许可，看许可的个数，获取指定的许可完毕，剩下的没有获取许可的线程放弃执行权限
                    if (semaphore.tryAcquire()){
                        System.out.println(Thread.currentThread().getName() + ":aquire() at time:" + System.currentTimeMillis());
                        Thread.sleep(1000);
                        semaphore.release();// 释放一个许可
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
