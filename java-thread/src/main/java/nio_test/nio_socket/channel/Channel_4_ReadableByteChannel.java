package nio_test.nio_socket.channel;
import org.junit.Test;
/**
 * @ClassName nio_test.nio_socket.channel.Channel_4_ReadableByteChannel
 * @Deacription :ReadableByteChannel  是表示允许 通道对字节进行读操作；
 *                  ReadableByteChannel 接口只允许1个读操作正在进行。
 *                  如果 1 个线程正在 通道上进行read()操作，那么任何发起的read操作都会被阻塞，直到这个进行的线程操作完成；
 *
 *                  其他类型的 io 操作是否可以与read()操作同时进行，取决于通道的类型或者实现；
 *
 *                  ReadableByteChannel 有以下两个特点：
 *                  1.将通道当前位置中的字节序列读入一个ByteBuffer中；
 *                  2.read(ByteBuffer) 方法是同步的；
 *
 *               通道只接受以字节为单位的数据进行传输，因为通道和操作系统进行交互时，操作系统只接受字节数据；
 *
 *
 * @Author LP
 * @Date 2021/6/22
 * @Version 1.0
 **/
public class Channel_4_ReadableByteChannel {
    @Test
    public void test_01(){
        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable
                                               //ReadableByteChannel->Channel->Closeable->AutoCloseable

    }
}

