package nio_test.netty_.heartbeat;

import java.util.Random;

public class E {
    public static void main(String[] args) {
        Random random = new Random();
        int cou = 0;
        for (; ; ) {
            cou++;
            System.out.println("cou:" + cou + "," + (random.nextInt(10)));
            if (cou > 100) return;
        }
    }
}
