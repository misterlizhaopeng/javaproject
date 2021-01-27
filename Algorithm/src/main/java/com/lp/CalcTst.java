package com.lp;

import org.junit.Test;

import java.util.*;

/**
 * @ClassName com.lp.CalcTst
 * @Deacription TODO
 * @Author LP
 * @Date 2020/10/23 17:58
 * @Version 1.0
 **/
public class CalcTst {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = Integer.parseInt(in.nextLine());
        Map<String,String> map = new HashMap<String,String>();
        while(num>0) {
            String keyVue = in.nextLine();
            String[] keys = keyVue.trim().split(" ");
            if (map.keySet().contains(keys[0])) {
                map.put(keys[0],Integer.parseInt(keys[1])+Integer.parseInt(map.get(keys[0]))+"");
            }else{
                map.put(keys[0],keys[1]);
            }
            num--;
        }
        String[] arr = map.keySet().toArray(new String[0]);
        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int i = Integer.parseInt(o1);
                int i1 = Integer.parseInt(o2);
                return i-i1;
            }
        });

      /*  Set<Integer> setkey = map.keySet();
        Integer[] arr = new Integer[setkey.size()];
        setkey.toArray(arr);
        Arrays.sort(arr);*/



        for (String str: arr) {
            System.out.println(str + " " + map.get(str));
        }
    }


    @Test
    public void testLastLenFrmStr() {
        /*
        for (Integer[] t : output) {
            System.out.println(Arrays.toString(t));
        }


         */
        Scanner in = new Scanner(System.in);
        Integer _tmpStr = Integer.parseInt(in.nextLine());

        String calcStr = "This is a statement     a";
        String[] splitStr = calcStr.split(" ");
        String lastStr = splitStr[splitStr.length - 1];
        System.out.println(lastStr.length());
    }
}

