package thd_01;

public class ThreadIns extends Thread {
    @Override
    public void run() {
        System.out.println("当前--子线程的名字：" + this.getName());
        //System.out.println("当前--子线程的名字：" + Thread.currentThread().getName());
        // System.out.println("当前--子线程的id："+Thread.currentThread().getId());
    }
}
