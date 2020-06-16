package com.lp;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//        System.out.println(1 << 30);
//        System.out.println("------------------------------------>");

//        String[] arr = {};
//        arr[0] = "1";
//        arr[2] = "2";


//        System.out.println("李四".hashCode());
//        System.out.println("zhang".hashCode());
//        System.out.println("zhang".hashCode() % 24);
//        System.out.println("zhang".hashCode() % 8);
//
//        System.out.println();
//        Map<Integer, Integer> map = new HashMap<>(2,1f);
//        Integer put = map.put(1, 1);
//        map.put(2, 1);
//        map.put(3, 1);
//        map.put(4, 1);
//        map.put(5, 1);
//        map.put(6, 1);
//
//        //map.put(1, 2)
//        System.out.println(put);

//        int n=5;
//        System.out.println(n - (n >>> 2));
//
//        ConcurrentHashMap<String,String> concurrentHashMap =new ConcurrentHashMap<>();
//        concurrentHashMap.put("a", "b");
//        concurrentHashMap.put("ab", "b");
//        System.out.println("count=" + concurrentHashMap.size());
//
//        System.out.println(concurrentHashMap.get("a"));
//
//
//        System.out.println(":<--<->"+(n << 10));
//        System.out.println(":<-->->"+(n >> 4));
//
//
//        LongAdder longAdder = new LongAdder();
//        longAdder.add(1);



//        Map<String,String> map=new HashMap<>();
//        map.remove("a");
        Hashtable<String,String> table =new Hashtable<>();
        table.put("a", "b");
    }
}
