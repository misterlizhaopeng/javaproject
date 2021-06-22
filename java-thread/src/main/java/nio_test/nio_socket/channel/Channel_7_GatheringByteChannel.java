package nio_test.nio_socket.channel;

import org.junit.Test;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_7_GatheringByteChannel
 * @Deacription : GatheringByteChannel 的作用是：将多个缓冲区的数据写入通道中；
 *
 * @Author LP
 * @Date 2021/6/22
 * @Version 1.0
 **/
public class Channel_7_GatheringByteChannel {
    @Test
    public void test_01(){
        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable
        //                ScatteringByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                 GatheringByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
    }
}

