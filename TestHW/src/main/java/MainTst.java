import org.junit.Test;

/**
 * @ClassName PACKAGE_NAME.MainTst
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/22 9:10
 * @Version 1.0
 **/
public class MainTst {

    public static void main(String[] args) {

        System.out.println("aaa");



        SubObj subObj = new SubObj();
//        String obj = subObj.obj();
//        System.out.println("obj=" + obj);


    }


    private static TestObj testObj;

    @Test
    public void testA() {
        TestObj tt = new TestObj();
        tt.setId(1);
        tt.setName("xyz");
        System.out.println(tt.toString());
        testB(tt);
        System.out.println(tt.toString());
    }

    private void testB(TestObj t) {

//        TestObj testObj = new TestObj();
//        testObj.setName("aaa");
//        testObj.setId(123);


//        if (a > 0) {
//            TestObj testObj = new TestObj();
//        }

//        TestObj testObj = new TestObj();
//        testObj.setName("aaa");
//        testObj.setId(123);

        t.setName("az");

        System.out.println(t.toString());


    }
}

