package nio_test.nio_socket.channel;
import org.junit.Test;
/**
 * @ClassName nio_test.nio_socket.channel.Channel_3_AsynchronousByteChannel
 * @Deacription : AsynchronousByteChannel 是通道支持异步操作，操作单位：字节
 *                  在上一个read or write 操作未完成之前，再次进行调用 read or write( 与前面的对应起来 ) 操作会报异常（ReadPendingException or WritePendingException 异常）
 *
 *                  其他类型的通道是否支持同时进行 read 操作，取决于通道的类型或者实现；
 *
 *                  ByteBuffers类不是线程安全的类，操作这个时，尽量保持线程的唯一性；
 *
 * @Author LP
 * @Date 2021/6/22
 * @Version 1.0
 **/
public class Channel_3_AsynchronousByteChannel {
    @Test
    public void test_01(){
        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable

    }
}

