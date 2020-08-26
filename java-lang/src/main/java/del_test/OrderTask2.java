package del_test;

import java.util.concurrent.CountDownLatch;

public class OrderTask2 {
    public static void main(String[] args) {

        /**
         * 创建线程类的时候，将上一个计数器和本线程计数器传入。运行前业务执行上一个计数器.await, 执行后本计数器.countDown。
         */

        CountDownLatch num0 = new CountDownLatch(0);
        CountDownLatch num1 = new CountDownLatch(1);
        CountDownLatch num2 = new CountDownLatch(1);
        Thread t1 = new Thread(new Count(num0, num1));
        Thread t2 = new Thread(new Count(num1, num2));
        Thread t3 = new Thread(new Count(num2, num2));

        t1.start();
        t2.start();
        t3.start();
    }


    static class Count implements Runnable {

        CountDownLatch num1;
        CountDownLatch num2;

        /**
         * 该构造器传递了上一个线程的计数器和当前线程的计数器
         * 或者说：
         * 定义Work线程类，需要传入开始和结束的 CountDownLatch 参数
         *
         * @param num1
         * @param num2
         */
        public Count(CountDownLatch num1, CountDownLatch num2) {
            super();
            this.num1 = num1;
            this.num2 = num2;
        }

        @Override
        public void run() {
            try {
                num1.await();//前一线程为0才可以执行
                System.out.println("开始执行线程:" + Thread.currentThread().getName());
                num2.countDown();// 本线程计数器减少
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
