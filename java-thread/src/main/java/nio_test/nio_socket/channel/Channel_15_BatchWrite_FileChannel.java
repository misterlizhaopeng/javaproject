package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_15_BatchWrite_FileChannel
 * @Deacription : 批量写入通道    实现接口 GatheringByteChannel 的 write 方法
 *                  1.测试：从通道的当前位置开始批量写入
 *                  2.测试：将缓冲区（buffer）的 remaining 内容批量写入通道
 *                  3. 验证 write(bytebuffer-s) 方法对通道进行批量写的同步性
 * @Author LP
 * @Date 2021/7/3
 * @Version 1.0
 **/
public class Channel_15_BatchWrite_FileChannel {


    /**
     * 验证 write(bytebuffer-s) 方法对通道进行批量写的同步性
     * aaaa 和 bbbb 一直是一组；1111 和 2222 一直是一组；说明多线程下，对通道批量写的同步性！！
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException, InterruptedException {

        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFile("w3.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();

        for (int i = 0; i <10; i++) {
            // 多线程的情况下(此处创建三个线程)，对管道进行批量写操作；
            new Thread(()->{
                //创建两个缓冲区：bf1，bf2
                ByteBuffer bf1 = ByteBuffer.wrap("aaaa\r\n".getBytes());
                ByteBuffer bf2 = ByteBuffer.wrap("bbbb\r\n".getBytes());
                ByteBuffer[] bs={bf1,bf2};
                try {
                    fileChannel.write(bs);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

            new Thread(()->{
                //创建两个缓冲区：bf1，bf2
                ByteBuffer bf1 = ByteBuffer.wrap("1111\r\n".getBytes());
                ByteBuffer bf2 = ByteBuffer.wrap("2222\r\n".getBytes());
                ByteBuffer[] bs={bf1,bf2};
                try {
                    fileChannel.write(bs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(3000);
        //关闭资源
        fileOutputStream.close();
        fileChannel.close();
    }
    /**
     * 测试：将缓冲区（buffer）的 remaining 内容写入通道
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.wrap("ABC123".getBytes());
        byteBuffer.position(2);
        byteBuffer.limit(4);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFile("w2.txt")));
        //通道
        FileChannel fileChannel = fileOutputStream.getChannel();
        //向通道中写入数据
        fileChannel.write(byteBuffer);
        //把值：C1 写入文件中；


        ByteBuffer b1 = ByteBuffer.wrap("abcdefg".getBytes());
        ByteBuffer b2 = ByteBuffer.wrap("456789".getBytes());

        b1.position(2);
        b1.limit(4); //remaining  的值为：cd

        b2.position(2);
        b2.limit(4);//remaining  的值为：67
        ByteBuffer [] bs = { b1,b2 };
        fileChannel.write(bs);

        fileChannel.close();
        fileOutputStream.close();
    }

    /**
     * 测试：从通道的当前位置开始写入
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {

        //如果没有创建文件，w1.txt文件自动创建；
        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFile("w1.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.write(ByteBuffer.wrap("123455".getBytes()));

        //设置通道位置
        fileChannel.position(3);

        //再创建两个缓冲区byteBuffer1，byteBuffer2
        ByteBuffer byteBuffer1 = ByteBuffer.wrap("aaaa".getBytes());
        ByteBuffer byteBuffer2 = ByteBuffer.wrap("bbbb".getBytes());
        ByteBuffer[] byteBuffers = {byteBuffer1,byteBuffer2};
        //批量写入通道
        long write = fileChannel.write(byteBuffers);

        fileChannel.close();
        fileOutputStream.close();
        //结果：123aaaabbbb
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

