package nio_test.nio_socket.channel;

import org.junit.Test;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_8_ByteChannel
 * @Deacription :ByteChannel 接口：本身没有增加任何方法，只是同时继承了接口 ReadableByteChannel、WritableByteChannel;
 *                                 这样，ByteChannel接口就同时有了即可以写又可以读的 双向 的功能；
 *                                 ReadableByteChannel接口  只能从管道channel中读到缓冲区；
 *                                 WritableByteChannel接口 只能从缓冲区中写入管道中；
 *
 *
 * @Author LP
 * @Date 2021/6/22
 * @Version 1.0
 **/
public class Channel_8_ByteChannel {
    @Test
    public void test_01(){

    }
}

