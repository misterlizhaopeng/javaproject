package tl.executors;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


// 通过线程池，实现并发任务，然后对每一个任务进行接收返回值操作；
public class ThreadPoolFuture02 {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> fl = new ArrayList<>();

        // 创建10 个任务
        for (int i = 0; i < 10; i++) {
            Future<String> future = executorService.submit(new TaskWithResult(i));
            //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            fl.add(future);
        }

        //遍历任务的结果
        for (Future<String> _fl : fl) {
            try {
                //Future返回如果没有完成，则一直循环等待，直到 Future返回完成
                while (!_fl.isDone()) ;
                System.out.println(_fl.get());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // 关闭线程池
                executorService.shutdown();
            }
        }
    }
    @Test
    public void test01() {

    }
}

class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    /**
     * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行
     */
    public String call() throws Exception {
        System.out.println("call()方法被自动调用！！！    " + Thread.currentThread().getName());
        //该返回结果将被Future的get方法得到
        return "call()方法被自动调用，任务返回的结果是：" + id + "    " + Thread.currentThread().getName();
    }
}