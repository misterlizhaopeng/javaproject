package tl.sy_aqs_2_1;

import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    public static void main(String[] args) {
        ReentrantLock reentrantLock=new ReentrantLock();
        reentrantLock.lock();

        System.out.println("234");
        
        reentrantLock.unlock();
    }
}
