package tl.sy_aqs_2;

public class CancelLock {
    StringBuffer stb = new StringBuffer();


    public void test1(){
        //jvm的优化，锁的消除
        stb.append("1");

        stb.append("2");

        stb.append("3");

        stb.append("4");
    }

}
