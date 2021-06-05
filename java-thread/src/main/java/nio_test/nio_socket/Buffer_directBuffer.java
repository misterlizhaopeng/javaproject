package nio_test.nio_socket;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_directBuffer
 * @Deacription 直接和非直接缓冲区
 * @Author LP
 * @Date 2021/6/5 17:13
 * @Version 1.0
 **/
public class Buffer_directBuffer {
    @Test
    public void test(){
        CharBuffer charBuffer = CharBuffer.allocate(100);
        System.out.println(String.format("是否为直接缓冲区：%s",charBuffer.isDirect()));

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        System.out.println(String.format("是否为直接缓冲区：%s",byteBuffer.isDirect()));

        //直接缓冲区，比如，在读磁盘数据的时候，通过直接缓冲区的功能，就可以减少了在jvm中创建中间缓冲区，直接使用内核空间就进行处理了；
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(100);
        System.out.println(String.format("是否为直接缓冲区：%s",allocateDirect.isDirect()));
    }
}

