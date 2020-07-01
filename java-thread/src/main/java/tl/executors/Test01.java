package tl.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test01 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?> future = executorService.submit(() -> {
            try {
                System.out.println("5s后输出...start...");
                Thread.sleep(5000);
                System.out.println("end");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        if (future.isDone()) {
            System.out.println("5s任务完成");
            try {
                System.out.println("返回结果：" + future.get());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("5s任务 没有 完成");
        }

        executorService.shutdown();
    }



}
