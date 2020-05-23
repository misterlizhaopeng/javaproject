package thread_lp;

public class TestDeadThread {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        new Thread(new DeadThread(obj1, obj2),"dt1").start();
        new Thread(new DeadThread(obj2, obj1),"dt2").start();
    }
}