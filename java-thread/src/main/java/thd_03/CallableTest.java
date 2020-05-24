package thd_03;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws Exception {
        int len = 5;
        CountDownLatch countDownLatch = new CountDownLatch(len);
        for (int i = 0; i < len; i++) {
            //接口Callable 下面就一个方法：输入参数为空，返回值为泛型
            FutureTask<String> integerFutureTask = new FutureTask<>(() -> {


                System.out.println("当前线程名称：" + Thread.currentThread().getName());

                countDownLatch.countDown();
                return Thread.currentThread().getId() + ";" + Thread.currentThread().getName();
            });
            new Thread(integerFutureTask).start();
            System.out.println(integerFutureTask.get());//通过接口callable 接口，可以返回当前线程的值
        }

        countDownLatch.await();
        System.out.println("所有的线程执行完毕");
    }
}
