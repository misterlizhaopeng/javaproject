package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_17_PartBatchWrite_FileChannel
 * @Deacription : 部分批量写操作：
 *                  write 参数解释如下：bs：缓冲区数组，offset：从缓冲区集合的那个缓冲区开始，length，向后使用缓冲区的个数，
 *                  然后把所有缓冲区的remaining写入通道的当前位置;
 * <p>
 *                  1.验证write(bs,offset,length) 方法，从通道当前位置开始写数据
 *                  2.将每一个buffer的的remaining的内容写入通道
 *                  3.部分写操作：测试写操作的有序性（xxxx和yyyy一定是一起出现，1111和2222一定是一组出现，两组不会出现交错，测试结果也是这个样）
 * @Author LP
 * @Date 2021/7/5
 * @Version 1.0
 **/
public class Channel_17_PartBatchWrite_FileChannel {

    /**
     * 部分写操作：测试写操作的有序性（xxxx和yyyy一定是一起出现，1111和2222一定是一组出现，两组不会出现交错，测试结果也是这个样）
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_02() throws IOException, InterruptedException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFile("r_t_w.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(() -> {
                ByteBuffer b1 = ByteBuffer.wrap("xxxx\r\n".getBytes());
                ByteBuffer b2 = ByteBuffer.wrap("yyyy\r\n".getBytes());
                ByteBuffer[] bs = new ByteBuffer[]{b1, b2};
                try {
                    long writeLen = fileChannel.write(bs, 0, 2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread t2 = new Thread(() -> {
                ByteBuffer b1 = ByteBuffer.wrap("1111\r\n".getBytes());
                ByteBuffer b2 = ByteBuffer.wrap("2222\r\n".getBytes());
                ByteBuffer[] bs = new ByteBuffer[]{b1, b2};
                try {
                    long writeLen = fileChannel.write(bs, 0, 2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t1.start();
            t2.start();
        }
        Thread.sleep(3000);
        fileOutputStream.close();
        fileChannel.close();

    }


    /**
     * 1.验证write(bs,offset,length) 方法，从通道当前位置开始写数据
     * 2.将每一个buffer的的remaining的内容写入通道
     *
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFile("p_w.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();

        int writeLen = fileChannel.write(ByteBuffer.wrap("qqqqq".getBytes()));
        System.out.println(String.format("先写入的字节数：%s", writeLen));

        //设置管道位置
        fileChannel.position(2);

        //创建多个缓冲区
        ByteBuffer b1 = ByteBuffer.wrap("abcde".getBytes());
        b1.position(2);
        b1.limit(3);
        ByteBuffer b2 = ByteBuffer.wrap("1234".getBytes());
        b2.position(1);
        b2.limit(3);
        ByteBuffer[] bs = new ByteBuffer[]{b1, b2};

        //把部分缓冲区的内容写入管道
        long wLen = fileChannel.write(bs, 0, 1);
        System.out.println(String.format("先写入的字节数：%s", wLen));
        fileChannel.close();
        fileOutputStream.close();
    }


    /**
     * 文件路径
     *
     * @param fileName
     * @return
     */
    private String getFile(String fileName) {
        String str = "f:\\del\\testFile\\";
        str += fileName;
        return str;
    }
}

