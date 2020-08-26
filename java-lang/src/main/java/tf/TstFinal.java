package tf;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TstFinal {
    private static final Integer i=0;
    private static final String str = "";
    private static final Map map = new ConcurrentHashMap();
    private static final Map map2 = new HashMap();

    public static void main(String[] args) {
        //i=1; //静态整数不能修改 , 否则编译时期会出错
        //str = "zzz";//静态字符串不能修改,否则编译时期会出错

        System.out.println(str);

        //map = new ConcurrentHashMap();//静态map对象不能修改,否则编译时期会出错
        //map2=new HashMap();//静态map2对象不能修改,否则编译时期会出错

        map.put("a", 1);
        map.put("b", 1);


        final String strs="bbb";
        //strs="a";//内部变量strs定义之后不能修改，否则编译时时期会出错；



    }
}
