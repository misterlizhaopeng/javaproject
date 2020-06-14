package frm_kc;

public class TestWhile {
    public static void main(String[] args) {
        int a = 1;
        while (a < 64) {
            a <<= 1;
        }
        System.out.println(a);
    }
}
