package thread_lp;

import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

public class testThreadStatus {
    public static void main(String[] args) {

        //该方法的代码主要用于测试jConsole监视线程的状态
        // 锁对象
        Object obj = new Object();

        // 首先对象 Scanner 在一个方法里面，会保证上下文线程启动顺序，
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        System.out.println(next);//输出此处，执行下面T1线程(该线程的状态将会为：TIMED_WAITING)
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(100);
//                        System.out.println("T1");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, "T1").start();

        System.out.println(scanner.next());//输出此处，执行下面T2线程(该线程的状态将会为：java.lang.Object@1b7bbeb7上的WAITING)
        new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (obj) {
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "T2").start();
    }
}
