package del_test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

// 队列 保证 线程 顺序的执行
public class OrderTask {
    public static void main(String[] args) {
        ArrayBlockingQueue mq=new ArrayBlockingQueue(3,true);
        Thread t1 =new Thread(()->{
            System.out.println("one thread");
        },"t1");

        Thread t2 =new Thread(()->{
            System.out.println("two thread");
        },"t2");

        Thread t3 =new Thread(()->{
            Thread.currentThread().getName();
            System.out.println("three thread");
        },"t3");

        mq.add(t3);
        mq.add(t2);
        mq.add(t1);

        for (int i = 0; i < 3; i++) {
            Thread t = (Thread)mq.poll();//.peek();
            t.start();

            try {
                Thread.sleep(1000);
            }catch (Exception ex){
                ex.printStackTrace();
            }


        }

    }
}
