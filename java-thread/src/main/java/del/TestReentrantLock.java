package del;


import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {


    ReentrantLock reentrantLock = new ReentrantLock(true);

    public static void main(String[] args) {

    }


    public void exeMain() {
        reentrantLock.lock();
        try {
            System.out.println("xxxxxxxxxxxx");
        } finally {
            reentrantLock.unlock();
        }

    }
}
