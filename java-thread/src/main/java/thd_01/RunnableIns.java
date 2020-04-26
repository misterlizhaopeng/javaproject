package thd_01;

public class RunnableIns extends  A implements Runnable  {
    @Override
    public void run() {

        printCon();
        //System.out.println("当前->子线程的名称：" + Thread.currentThread().getName());
    }
}
