package del;

public class TestMyThread extends Thread {


    //共享变量
    private int a = 5;

    //重写run方法
    @Override
    public void run() {
        a--;
        System.out.println("a is :" + a);
    }

    public static void main(String[] args) {


        //实列需要多线程的对象
        TestMyThread myThread = new TestMyThread();


        //创建多个线程实列
        Thread t1 = new Thread(myThread, "t1");
        Thread t2 = new Thread(myThread, "t2");
        Thread t3 = new Thread(myThread, "t3");
        Thread t4 = new Thread(myThread, "t4");
        Thread t5 = new Thread(myThread, "t5");



        //运行
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


    }


}