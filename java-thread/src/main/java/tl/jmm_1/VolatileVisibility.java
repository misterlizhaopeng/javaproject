package tl.jmm_1;

public class VolatileVisibility {
    public static int i = 0;

    public static void increase() {
        i++;
    }

    static Object obj = new Object();

    public static void main(String[] args)  {
        for (int j = 0; j < 100; j++) {
            new Thread(() -> {
                synchronized (obj) {
                    increase();
                }
            }).start();
        }
        System.out.println(i);
    }
}
