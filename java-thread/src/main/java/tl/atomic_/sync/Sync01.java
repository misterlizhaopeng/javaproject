package tl.atomic_.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 修饰代码块、修饰方法
public class Sync01 {
    /**
     * synchronized
     * 修饰代码块：写法：大括号括起来的代码，作用与调用的对象
     * 修饰方法：写法：整个方法，作用与调用的对象
     * 修饰静态方法：写法：整个静态方法，作用于所有对象
     * 修饰类：写法：括号括起来的部分，作用于所有对象
     */
    // 修饰一个代码块
    public void test1(int j) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println("test1 j=" + j + ",i=" + i);
            }
        }
    }

    // 修饰一个方法
    public synchronized void test2(int j) {
        for (int i = 0; i < 100; i++) {
            System.out.println("test2 j=" + j + ",i=" + i);
        }
    }

    public static void main(String[] args) {
        Sync01 example1 = new Sync01();
        Sync01 example2 = new Sync01();
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 同一个对象调用加锁方法：顺序执行 测试-start

//
//            //通过线程池 创建一个线程，执行任务
//            executorService.execute(() -> {
//                example1.test2(1);
//            });
//
//            //通过线程池 创建一个线程，执行任务
//            executorService.execute(() -> {
//                example1.test2(2);
//            });

        // 同一个对象调用加锁方法：顺序执行 测试-end


        // 不同对象调用加锁方法：交替执行 测试-start

            //通过线程池 创建一个线程，执行任务
            executorService.execute(() -> {
                example1.test2(1);
            });

            //通过线程池 创建一个线程，执行任务
            executorService.execute(() -> {
                example2.test2(2);
            });

        // 不同对象调用加锁方法：交替执行 测试-end
        executorService.shutdown();
    }


}
