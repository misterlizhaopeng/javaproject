package list_;

import java.util.Random;

/**
 * @ClassName list_.NonStopMachine
 * @Deacription TODO
 * @Author LP
 * @Date 2021/3/26 22:14
 * @Version 1.0
 **/
public class NonStopMachine {
    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            Thread.sleep(1000);
            Random random = new Random();
            int i = random.nextInt(100);
            System.out.println(NonStopMachine.class.getName() + ":" + i);
        }
    }
}

