package del_test;

public class TestConst {
    public static void main(String[] args) {
//        String str1 = "abc";
//        String str2 = "abc";
//        String str3 = "abc";

//        String str4 = new String("abc");
//        String str5 = new String("abc");
//        String str6 = new String("abc");


        // 5 种整形的包装类 Byte,Short,Integer,Long,Character的对象
        // 在值小于127时可以使用常量池
        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println(i1 == i2);//输出true

        //值大于127时，不会从常量池中取对象
        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4);//输出false

        //Boolean类也实现了常量池技术
        Boolean b1 = true;
        Boolean b2 = true;
        System.out.println(b1 == b2);//输出true


        //浮点类型的包装类没有实现常量池技术
        Double d1 = 1.0;
        Double d2 = 1.0;
        System.out.println(d1 == d2);//输出false


    }
}
