package nio_test.nio_socket.channel;

import org.junit.Test;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_11_MulticastChannel
 * @Deacription : MulticastChannel:表示通道支持ip多播，详细意思就是：允许通道向多个主机发送数据信息；
 * @Author LP
 * @Date 2021/6/23
 * @Version 1.0
 **/
public class Channel_11_MulticastChannel {
    @Test
    public void test_01(){

        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable
        //                ScatteringByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                 GatheringByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //                          ByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                          ByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //     SeekableByteChannel->ByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //                          MulticastChannel->NetworkChannel->Channel->Closeable->AutoCloseable
    }
}

