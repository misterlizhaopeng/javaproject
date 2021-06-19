package nio_test.nio_socket.buffer;
import org.junit.Test;

import java.nio.CharBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_limit_position_lr
 * @Deacription limit和position之前是否可以来回覆盖-测试
 * @Author LP
 * @Date 2021/6/5 16:52
 * @Version 1.0
 **/
public class Buffer_limit_position_lr {
    @Test
    public void test(){
        char[] chars =new char[]{'1','b','c','d'};
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        System.out.println(String.format("position=%s,limit=%s",charBuffer.position(),charBuffer.limit()));

        charBuffer.limit(2);
        System.out.println(String.format("position=%s,limit=%s",charBuffer.position(),charBuffer.limit()));

        //上面设置了limit为2 的位置，position为0，此处立马设置position为3，超过limit，来看下limit的变化：此种情况会出现异常
        charBuffer.position(3);
        System.out.println(String.format("position=%s,limit=%s",charBuffer.position(),charBuffer.limit()));
    }
    @Test
    public void test2(){
        char[] chars =new char[]{'1','b','c','d'};
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        System.out.println(String.format("position=%s,limit=%s",charBuffer.position(),charBuffer.limit()));

        charBuffer.position(3);
        System.out.println(String.format("position=%s,limit=%s",charBuffer.position(),charBuffer.limit()));

        //上面设置了position为3 的位置，limit为3，此处立马设置limit为2，超过position，来看下position的变化：
        // 结论：在设置limit索引时，如果limit 的索引变得比position还小，那么position的值就是limit的新值,
        // 正常情况下：limit可以往左推覆盖position，但是position不能往右推覆盖limit，否则会报异常；
        charBuffer.limit(2);
        System.out.println(String.format("position=%s,limit=%s",charBuffer.position(),charBuffer.limit()));
    }
}

