package nio_test.nio_socket.channel;
import org.junit.Test;
/**
 * @ClassName nio_test.nio_socket.channel.Channel_5_ScatteringByteChannel
 * @Deacription :ScatteringByteChannel 主要作用是从通道中 读取字节 到多个缓冲区中；
 *
 *                  scattering：[ˈskætərɪŋ] 撒; 撒播; 散开; 四散; 使分散; 驱散;
 * @Author LP
 * @Date 2021/6/22
 * @Version 1.0
 **/
public class Channel_5_ScatteringByteChannel {
    @Test
    public void test_01(){
        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable
        //                ScatteringByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable

    }
}

