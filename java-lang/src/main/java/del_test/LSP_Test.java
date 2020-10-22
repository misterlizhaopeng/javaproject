package del_test;

import jdk.internal.dynalink.beans.StaticClass;

public class LSP_Test {
    private static final String Mess = "taobao";

    public static void main(String[] args) {
        String a = "tao" + "bao";
        String b = "tao";
        String c = "bao";
        String d = b + c;
        System.out.println((a == Mess));
        System.out.println((b + c) == Mess);
        System.out.println(d == Mess);
        // synchronized
    }
}