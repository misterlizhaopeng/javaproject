package del_test;


import java.util.Arrays;
import java.util.List;

public class Test {
    private static final String[] arr = {"1", "5", "90"};
    private static String[] arr2 = {"21", "22", "23"};

    public static void main(String[] args) {
        System.out.println(arr.length);
        System.out.println(arr2.length);

        arr[2] = "1";
        arr2[arr2.length] = "2";


        //view
        List<String> list = Arrays.asList(arr);
        list.forEach(a -> {
            System.out.println(a);
        });

        System.out.println("<------------------------------->");

        List<String> list2 = Arrays.asList(arr2);
        list2.forEach(a -> {
            System.out.println(a);
        });

//        System.out.println(arr);
    }
}

class MyClass {

}