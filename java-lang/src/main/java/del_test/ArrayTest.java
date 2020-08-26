package del_test;

import java.util.Arrays;
import java.util.List;

public class ArrayTest {
    private static String[] arr = new String[10];

    public static void main(String[] args) {
        arr[0] = "1";
        arr[1] = "2";
        List<String> list = Arrays.asList(arr);
        list.forEach(a -> {
            System.out.println(a);
        });

        String[] arr2 = {"21", "22", "23"};
        System.out.println("arr2's length=" + arr2.length);
        arr2[3] = "24";
    }
}
