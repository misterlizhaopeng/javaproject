package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_16_BatchRead_FileChannel
 * @Deacription : 批量从通道读取内容 : 实现接口 ScatteringByteChannel 的read方法
 *                  1.验证long read(bytebuffers ) 返回值的意义
 *                  2.
 *
 * @Author LP
 * @Date 2021/7/3
 * @Version 1.0
 **/
public class Channel_16_BatchRead_FileChannel {

    /**
     *  验证long read(bytebuffers ) 返回值的意义；返回读取的字节数
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_01() throws IOException, InterruptedException {
        //rr.txt  的内容为：abcde
        FileInputStream fileInputStream = new FileInputStream(new File(getFile("rr.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer b1 = ByteBuffer.allocate(2);
        ByteBuffer b2 = ByteBuffer.allocate(2);
        ByteBuffer [] bs = { b1, b2 };
        long readLen = fileChannel.read(bs);
        System.out.println(String.format("a 取得字节数量为：%s",readLen));//4
        b1.clear();
        b2.clear();

        readLen = fileChannel.read(bs);
        System.out.println(String.format("b 取得字节数量为：%s",readLen));//1
        b1.clear();
        b2.clear();

        readLen = fileChannel.read(bs);
        System.out.println(String.format("b 取得字节数量为：%s",readLen));//-1
        b1.clear();
        b2.clear();

        fileChannel.close();
        fileInputStream.close();
    }


    /**
     * 文件路径
     * @param fileName
     * @return
     */
    private String getFile(String fileName) {
        String str = "f:\\del\\testFile\\";
        str += fileName;
        return str;
    }

}

