package nio_test.tes_file;

/**
 * @ClassName nio_test.tes_file.TestThreadStyle
 * @Deacription TODO
 * @Author LP
 * @Date 2021/6/1 14:42
 * @Version 1.0
 **/
public class TestThreadStyle {
    public static void main(String[] args) {

        System.out.println("start");

        Demo demo = new Demo();
        demo.start();
        System.out.println("end");

        System.out.print("thread id= " + Thread.currentThread().getId() + "\r\n");
        System.out.println("thread name=" + Thread.currentThread().getName());


        new Thread(new demo2()).start();

        //第三种创建线程的方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.print("thread id= " + Thread.currentThread().getId() + "\r\n");
                System.out.println("thread name=" + Thread.currentThread().getName());
            }
        }).start();

        //第四种创建线程的方式：第三种创建线程的方式的简写
        new Thread(()->{
            System.out.print("thread id= " + Thread.currentThread().getId() + "\r\n");
            System.out.println("thread name=" + Thread.currentThread().getName());
        }).start();

    }
}


//第一种创建线程的方式
class Demo extends Thread {
    @Override
    public void run() {
        System.out.print("thread id= " + Thread.currentThread().getId() + "\r\n");
        System.out.println("thread name=" + Thread.currentThread().getName());
    }
}

abstract class DemoParent {
}

//第二种创建线程的方式，这种方式比第一种方式的好处：多继承一个类，第一种方式就不能继承额外的类了
class demo2 extends DemoParent implements Runnable {
    @Override
    public void run() {
        System.out.print("thread id= " + Thread.currentThread().getId() + "\r\n");
        System.out.println("thread name=" + Thread.currentThread().getName());
    }
}
