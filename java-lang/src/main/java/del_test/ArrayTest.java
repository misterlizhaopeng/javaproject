package del_test;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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


    @Test
    public void testHashMap(){
        Map<String,String> map=new HashMap<>();
        for (int i = 0; i < 13; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }

        ConcurrentHashMap<String, Object> cuMap = new ConcurrentHashMap<>();
        int size = cuMap.size();



    }
}
