package tl.sy_aqs_2_1;

import java.util.concurrent.Semaphore;

// 一次最多获取指定个数的许可，才能执行，当释放许可之后，在接着执行剩下的获取许可的线程；
public class Semaphore03 {
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();//获取一个许
                    System.out.println(Thread.currentThread().getName() + ":aquire() at time:" + System.currentTimeMillis());
                    Thread.sleep(1000);
                    semaphore.release();// 释放一个许可
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
