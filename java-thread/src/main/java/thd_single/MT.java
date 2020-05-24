package thd_single;

public class MT {
    public static void main(String[] args) {

/*        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    EHParttern ehParttern = EHParttern.getEhParttern();
                    System.out.println("thread id=" + Thread.currentThread().getId() + ",ins=" + ehParttern);
                }
            }).start();
        }*/

        //lambda
   /*     for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                EHParttern ehParttern = EHParttern.getEhParttern();
                System.out.println("thread name=" + Thread.currentThread().getName() + ",ins=" + ehParttern);
            }).start();
        }*/


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LHPattern lhPatternIns = LHPattern.getLHPatternIns();
                System.out.println("thread name=" + Thread.currentThread().getName() + ",ins=" + lhPatternIns);

            }).start();

            if (i == 9)
                System.out.println("--------------------------------->"+i);

        }


    }
}
