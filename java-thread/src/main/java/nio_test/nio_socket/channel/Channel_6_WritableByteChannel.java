package nio_test.nio_socket.channel;

import org.junit.Test;

import java.nio.channels.GatheringByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_6_WriteableByteChannel
 * @Deacription : WritableByteChannel 表示：使通道允许对字节进行写操作；
 *                  WritableByteChannel 接口只允许 1 个线程再进行，其他的操作需要等待，直到这个写线程完成；
 *
 *                  其他的类型的 io 操作是否可以同时进行，取决于通道的类型；
 *
 *                  WritableByteChannel 有以下两个特点：
 *                  1.将 1 个字节缓冲区的字节序列 【写入通道的当前位置】；
 *                  2.write(ByteBuffer byteBuffer) 是同步的；
 *
 * @Author LP
 * @Date 2021/6/22
 * @Version 1.0
 **/
public class Channel_6_WritableByteChannel {
    @Test
    public void test_01(){
        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable
        //                ScatteringByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                                       WritableByteChannel->Channel->Closeable->AutoCloseable



    }
}

