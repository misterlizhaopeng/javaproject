package thd_02;

public class BuyTicket implements Runnable {
    @Override
    public  void run() {
        //当前线程对公共资源操作-1
        // 当前线程获取锁后才可以进行操作-1
        synchronized (Ticket.class) {
            System.out.println("当前票数：" + Ticket.t);
            Ticket.t--;
            System.out.println("当前还剩票数：" + Ticket.t);
        }
    }
}
