package nio_test.nio_socket.channel;
import org.junit.Test;

import java.nio.channels.AsynchronousChannel;
import java.nio.channels.Channel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_1_Test
 * @Deacription :
 *              a.通道以及理解通道的层次结构，从源码层面可以看出来，nio 的通道功能非常强大
 *              b.什么是通道?
 *                  通道是用于 I/O 操作的链接，更具体来讲，通道代表数据到硬件设备、文件、网络套接字的链接；
 *                  通道可处于两种状态：打开、关闭；刚创建的通道是打开的状态，将其关闭，则处于关闭的状态；
 *                  当通道处于关闭状态，对其进行I/O操作，会抛ClosedChannelException异常，可以用isOpen()方法进行检测，查看通道是否处于打开状态；
 *                  一般情况下，通道对于多线程的访问是安全的；
 *              c.Channel :
 *                  interface Channel extends Closeable
 *                  interface Closeable extends AutoCloseable
 *              d.Channel 共有11个接口：
 *                  AsynchronousChannel、AsynchronousByteChannel、ReadableByteChannel、ScatteringByteChannel、WritableByteChannel、GatheringByteChannel、
 *                  ByteChannel、SeekableByteChannel、NetworkChannel、MulticastChannel、InterruptibleChannel;
 *              f.接口的介绍
 *
 * @Author LP
 * @Date 2021/6/22
 * @Version 1.0
 **/
public class Channel_1_Test {

    @Test
    public void test_01(){

        //Channel channel ;//= AsynchronousChannel;

        // try小括号如果存在多条语句，最后一条没有分号，并且小括号里面的变量都要实现接口 ：AutoCloseable
        try (DemoAutoCloseable demoAutoCloseable =new DemoAutoCloseable()){

            System.out.println("开始业务的操作");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

/**
 *AutoCloseable 接口强调的是与try()结合实现自动关闭，可以自动关闭，而不需要显示的调用 close 方法
 * 因为实现该接口是针对任何资源的，也就是说不仅仅I/O操作，所以返回的异常为 Exception;
 * 该接口不是幂等的，重复调用close()；会出现副作用；
 *
 *
 *
 * 而子接口Closeable 的close方法是关闭I/O流，释放资源的，所以方法抛出的异常为 IOException，是具有幂等性的，多次调用close方法不会有什么影响；
 * 还有就是接口Closeable 继承 接口 AutoCloseable，说明 Closeable  也有自动关闭功能；
 */
class DemoAutoCloseable implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("close links 。。-");
    }
}

