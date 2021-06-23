package nio_test.nio_socket.channel;

import org.junit.Test;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_9_SeekableByteChannel
 * @Deacription : SeekableByteChannel : 在字节通道中维护position位置，允许position发生改变；
 * @Author LP
 * @Date 2021/6/23
 * @Version 1.0
 **/
public class Channel_9_SeekableByteChannel {
    @Test
    public void test_01(){

        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable
        //                ScatteringByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                 GatheringByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //                          ByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                          ByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //     SeekableByteChannel->ByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable

    }
}

