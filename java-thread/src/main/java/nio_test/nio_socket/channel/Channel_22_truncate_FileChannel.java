package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_22_truncate_FileChannel
 * @Deacription : 截断通道文件给定大小！！
 *                  对于方法 truncate(long size)：
 *                      如果size 为小于文件大小，则截取该文件，丢弃文件新末尾文件的所有字节；
 *                      如果size大于文件大小，文件保持不变；
 *                      不论上述那种情况，如果通道位置大于给定size大小，则将该位置设置为该大小（懂了：把位置变化为size的值）
 *
 *                  结论：如何截取：经过当前测试，从头开始截取指定的size大小！！
 *
 * @Author LP
 * @Date 2021/7/11
 * @Version 1.0
 **/
public class Channel_22_truncate_FileChannel {
    /**
     *  size 的值大于等于文件大小，截取的文件内容不变
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("truncate_test.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.write(ByteBuffer.wrap("12345678".getBytes()));
        System.out.println(String.format("A 通道位置：%s,文件大小：%s",fileChannel.position(),fileChannel.size()));
        //截取文件的大小
        fileChannel.truncate(500);
        System.out.println(String.format("B 通道位置：%s,文件大小：%s",fileChannel.position(),fileChannel.size()));

    }

    /**
     * size 的值小于文件大小，则截取该文件，丢弃文件新末尾文件的所有字节；
     * 如何截取：经过当前测试，从头开始截取指定的size大小！！
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("truncate_test.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.write(ByteBuffer.wrap("12345678".getBytes()));
        fileChannel.position(10);//测试：truncate 和通道位置没关系
        System.out.println(String.format("A 通道位置：%s,文件大小：%s",fileChannel.position(),fileChannel.size()));
        //截取文件的大小
        fileChannel.truncate(5);
        System.out.println(String.format("B 通道位置：%s,文件大小：%s",fileChannel.position(),fileChannel.size()));
    }
}

