package thd_01;

public class A {

    public  void  printCon(){
        System.out.println("当前类："+this.getClass()+"，对应當前的线程，当前线程的名称："+Thread.currentThread().getName());
    }
}
