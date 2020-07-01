package del;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


// ScheduledExecutorService  演示：延迟指定时间执行任务的不同情况
public class ScheduleThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);


        //不管任务是否执行完毕，period 时间后启动当前任务
        ses.scheduleAtFixedRate(()->{
            System.out.println("this is atFixRate");
        }, 1, 3, TimeUnit.SECONDS);


        //等待任务执行完毕后，delay 时间后再启动一个任务
        ses.scheduleWithFixedDelay(()->{
            System.out.println("scheduleWithFixedDelay-》this is atFixRate");
        }, 1, 3, TimeUnit.SECONDS);

        // 需要招个合理的时机，进行关闭线程池
        //ses.shutdown();


        //Timer 定时器
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer-》this is Timer");
            }
            //第二个参数，我这里设置的为new Date()，表示第一次执行的时间，意思就是当前时间为第一次执行
            //第三个参数表示隔多久执行一次
        }, new Date(),2*1000);

        //Terminates this timer
        //timer.cancel();
    }
}
