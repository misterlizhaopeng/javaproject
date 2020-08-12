package del_test;

public class TestCls {
    public static void main(String[] args) {
        String str = "abc";
        String str1 = "abc123";
        String str2 = "abc123我们";
        System.out.println(str.getBytes().length);
        System.out.println(str1.getBytes().length);
        System.out.println(str2.getBytes().length);
    }
}
