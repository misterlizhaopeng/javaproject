package nio_test.nio_socket.channel;

import org.junit.Test;

import java.nio.channels.NetworkChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_10_NetworkChannel
 * @Deacription : NetworkChannel 的作用是使通道和socket关联，使通道中的数据能在 socket 中传输！
 *                  bind方法用于将socket 绑定到本地;
 * @Author LP
 * @Date 2021/6/23
 * @Version 1.0
 **/
public class Channel_10_NetworkChannel {
    @Test
    public void test_01(){

        //NetworkChannel
        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable
        //                ScatteringByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                 GatheringByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //                          ByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                          ByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //     SeekableByteChannel->ByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //                                            NetworkChannel->Channel->Closeable->AutoCloseable

    }
}

