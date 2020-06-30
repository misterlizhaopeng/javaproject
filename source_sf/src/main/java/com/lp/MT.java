package com.lp;

import java.util.HashMap;
import java.util.Map;

public class MT {
    public static void main(String[] args) {
        Person p1=new Person(1,"1");
//        Person p2=new Person(1,"2");
//        System.out.println(p1.equals(p2));
//
//
        Map<Person,String> map=new HashMap<>();
        map.put(p1, "aaa");
        System.out.println("name".hashCode());
        System.out.println("address".hashCode());
////        map.put(p2, "bbb");
//        System.out.println(map.get(p1));
//        System.out.println(map.get(p2));


//        if (!xp.red || (xpp = xp.parent) == null)
//            return root;

        System.out.println(1 >> 16);
        System.out.println(1 << 16); // 65535
        System.out.println(1 << 30); // 1073741824

        String key="a";
        int i = key.hashCode();
        System.out.println("hashCode="+i);
        int i1 = i >>> 16;
        System.out.println(i1);



        if (false || true)
            System.out.println(1);

        if (true || true)
            System.out.println(2);

        if (true || false)
            System.out.println(3);


    }
}
