package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_19_WriteDataToPositon_FileChannel
 * @Deacription : 向通道指定位置写数据
 * @Author LP
 * @Date 2021/7/8
 * @Version 1.0
 **/
public class Channel_19_WriteDataToPositon_FileChannel {
    /**
     *
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("wdp.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        //声明缓冲区
        ByteBuffer byteBuffer = ByteBuffer.wrap("abced".getBytes());
        int writeLen = fileChannel.write(byteBuffer);
        System.out.println(String.format("A 写入通道的字节长度为：%s",writeLen));

        //向通道的指定位置再次写入缓冲区的remaining数据内容
        byteBuffer.rewind();
        //byteBuffer.clear();
        writeLen = fileChannel.write(byteBuffer, 2);
        System.out.println(String.format("B 再次写入通道的字节长度为：%s",writeLen));

        //关闭通道资源
        fileChannel.close();
        //关闭输出字节流资源
        fileOutputStream.close();

    }
}

