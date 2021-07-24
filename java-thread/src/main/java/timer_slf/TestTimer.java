package timer_slf;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
    /**
     * 定时器：指定时间 2018-01-06 23:56:00 执行指定任务 TimerTask ， 10秒执行一次该任务
     *
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        Timer timer = new Timer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        timer.schedule(new TimerTask() {
            int i = 1;

            @Override
            public void run() {
                if (i >= 5) {
                    //结束定时器timer
                    timer.cancel();
                    System.out.println("abceee_" + i + "ss");
                } else {
                    String str = Thread.currentThread().getName();
                    System.out.println(String.format("%s", str + ":abceee_" + i));
                }
                i++;
            }
        }, dateFormat.parse("2018-01-06 23:56:00"), 1000 * 1);
    }

    @Test
    public void test_01() throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(String.format("%s", Thread.currentThread().getName()));
            }
        }, 5000);

        Thread.sleep(Integer.MAX_VALUE);
    }
}