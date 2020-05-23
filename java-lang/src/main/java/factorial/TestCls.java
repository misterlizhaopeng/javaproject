package factorial;

public class TestCls {
    public static void main(String[] args) {
        System.out.println(fac(10));
    }

    private static int fac(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * fac(n - 1);
        }

    }
}
