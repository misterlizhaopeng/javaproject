package frm_kc;


//单例：懒汉模式

public class TestSingleton_lh {


    private TestSingleton_lh() {
    }

    private volatile static TestSingleton_lh s = null;

    // 静态工程方法
    public static TestSingleton_lh getInstance() {
        if (s == null) {
            synchronized (TestSingleton_lh.class) {
                if (s == null) {//
                    s = new TestSingleton_lh();
                    System.out.println("--------> invoked by thread ,thread name is " + Thread.currentThread().getName());
                }
            }
        }
        return s;
    }
}
