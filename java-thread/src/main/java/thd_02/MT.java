package thd_02;

public class MT {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new BuyTicket()).start();
        }
    }
}
