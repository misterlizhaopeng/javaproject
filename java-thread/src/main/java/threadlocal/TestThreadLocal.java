package threadlocal;

import org.junit.Test;

public class TestThreadLocal {
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        System.out.println("mainThread," + Thread.currentThread().getName());
        int cou = 0;
        for (int i = 1; i <= 10; i++) {
            cou = cou + i;
            if (cou == 55)
                System.out.println("--------------------------------->" + i);


            new Thread(() -> {
                String name = Thread.currentThread().getName();
                System.out.println("subThread," + name);
                threadLocal.set(2 + "," + name);
                System.out.println(threadLocal.get());
                threadLocal.remove();
            }).start();
        }
    }
}
