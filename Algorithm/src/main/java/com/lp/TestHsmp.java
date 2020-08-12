package com.lp;

import java.util.HashMap;
import java.util.Map;

public class TestHsmp {
    public static void main(String[] args) {
        Map<String,String> hashMap=new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            hashMap.put(i+"_key", i+"_val");
        }
//        hashMap.put("1", "1");
//        hashMap.put("1", "2");//找到key为1 的node，把值改为2，返回oldValue为1 的值；
//        hashMap.put("2", "2");
//        hashMap.put("3", "3");
//        hashMap.put("4", "4");
//        hashMap.put("5", "5");
//        hashMap.put("6", "6");

        System.out.println(hashMap);


        MyCh myCh=new MyCh();
        myCh.put("abc", "ccc");
        System.out.println(myCh);
    }
}
