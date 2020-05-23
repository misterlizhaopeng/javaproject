package thread_lp;

/**
 * 类似创建一个Thread对象，然后让消费者调用（此例中是：cn.lp.TestDeadThread）
 *
 * @author misterLip
 * @date 2018年7月27日
 */

public class DeadThread implements Runnable {

    Object obj1 = null;
    Object obj2 = null;

    public DeadThread(Object obj1, Object obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    //个人经验：死锁的发生特点是：同步中嵌套同步
    @Override
    public void run() {
        synchronized (obj1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            synchronized (obj2) {
                System.out.println("Hello");
            }
        }
    }
}