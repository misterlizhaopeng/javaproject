package tl.sy_aqs_2;

public class TestSynchronized {

    Object obj = new Object();

    public static void main(String[] args) {
    }


    public void incre(int a, int b) {
        synchronized (obj) {
            int c = a + b;
        }
    }
    public synchronized void incre_b(int a, int b) {
            int c = a + b;
    }
}
