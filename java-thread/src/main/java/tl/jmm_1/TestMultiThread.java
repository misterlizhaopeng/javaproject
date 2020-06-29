package tl.jmm_1;

public class TestMultiThread {
    public static volatile Integer count = 1;

    public static void main(String[] args) {


        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            count++;
        }).start();

        Thread thread = new Thread(() -> {
            System.out.println(count);
        });
        thread.start();
        thread.interrupt();
        Thread.interrupted();


    }


}
