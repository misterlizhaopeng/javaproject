package del_test;

import java.util.concurrent.CopyOnWriteArrayList;

public class TstCopyonWrite_ {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> cow=new CopyOnWriteArrayList<>();
        cow.add(1);

        System.out.println(cow.contains(1));
        System.out.println(cow.get(0));


        CopyOnWriteArrayList<String> cowStr=new CopyOnWriteArrayList<>();
        cowStr.add("hello !");
        System.out.println("cowStr.get(0)="+cowStr.get(0));//取第一个值
        System.out.println("cowStr.get(1)="+cowStr.get(1));//取第二个值
    }
}
