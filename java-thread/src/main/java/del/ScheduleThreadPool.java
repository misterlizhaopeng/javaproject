package del;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);



        //不管任务是否执行完毕，period 时间后启动一个任务
        ses.scheduleAtFixedRate(()->{
            System.out.println("this is atFixRate");
        }, 1, 3, TimeUnit.SECONDS);


        //等待任务执行完毕后，delay 时间后再启动一个任务
        ses.scheduleWithFixedDelay(()->{
            System.out.println("scheduleWithFixedDelay-》this is atFixRate");
        }, 1, 3, TimeUnit.SECONDS);

    }
}
