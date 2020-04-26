package thd_01;

public class MT {
    public static void main(String[] args) {
        System.out.println("当前--主线程的名字：" + Thread.currentThread().getName());
        System.out.println("当前--主线程的id：" + Thread.currentThread().getId());
//        ThreadIns threadIns =new ThreadIns();
//        threadIns.start();
//        for (int i = 0; i < 10; i++) {
//            new ThreadIns().start();
//        }




        //runnable 线程定义
        for (int i = 0; i < 50; i++) {
           new Thread(new RunnableIns()).start();
        }
    }
}
