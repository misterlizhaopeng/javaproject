package frm_kc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 测试单例的懒汉模式-线程安全性
public class MT2 {
    public static void main(String[] args) throws Exception {
        int count=500;
        for (int i = 0; i < count; i++) {
            new Thread(()->{
                TestSingleton_lh instance = TestSingleton_lh.getInstance();
                //System.out.println(instance);
            }).start();

        }
    }
}
