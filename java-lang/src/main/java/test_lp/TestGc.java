package test_lp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class TestGc {
    static char[] ch = {'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd',
            'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd',
            'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd'
            , 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd'};



    public static void main(String[] args) throws Exception {
        ArrayList<Object> objects = new ArrayList<>();
        while (true){
            objects.add(new TestGc());
            Thread.sleep(1);
        }


        // -Xms2048m -Xmx2048m -XX:+PrintGCDetails
//        long maxMemory = Runtime.getRuntime().maxMemory(); // 返回 Java 虚拟机试图使用的最大内存量。
//        long totalMemory = Runtime.getRuntime().totalMemory();//返回 Java 虚拟机中的内存总量。
//        System.out.println("MAX_MEMORY = " + maxMemory + "（字节）、" + (maxMemory / (double) 1024 / 1024) + "MB");
//        System.out.println("TOTAL_MEMORY = " + totalMemory + "（字节）、" + (totalMemory / (double) 1024 / 1024) + "MB");
        //System.gc();
    }

    // -Xms1m -Xmx8m -XX:+HeapDumpOnOutOfMemoryError
    @Test
    public void test() throws Exception{
        String str = "first String";

        while (true) {
            StringBuilder sb=new StringBuilder();
            int i = new Random().nextInt(88888888) + new Random().nextInt(999999999);
            System.out.println(i);

            sb.append(str);
            sb.append(i);
            String s = sb.toString();
            System.out.println(s);
            sb=null;

            //str += str + i;

            Thread.sleep(1);
        }
    }
}
