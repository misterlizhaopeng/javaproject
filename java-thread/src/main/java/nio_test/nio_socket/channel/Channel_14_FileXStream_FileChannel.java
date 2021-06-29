package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_14_FileOutputStream_FileChannel
 * @Deacription : 1.验证通道write方法是从当前位置开始写入
 *                  2.测试fileChannel.write(bytebuffer): 在写入通道时，如何读取缓冲区 remaining 的
 *                  3.测试filechannel的write方法是同步的（多线程下是同步的，也就是一个线程做完独立的任务之后，再由下一个线程执行操作）
 * @Author LP
 * @Date 2021/6/29
 * @Version 1.0
 **/
public class Channel_14_FileXStream_FileChannel {

    /**
     * 测试filechannel的write方法是同步的
     * 文件结果：
     * 【
     *          这是一个不孬的测试程序
     *          这是一个不孬的测试程序
     *          agcdef
     *          这是一个不孬的测试程序
     *          agcdef
     *          agcdef
     *          agcdef
     *          agcdef
     *          这是一个不孬的测试程序
     *          这是一个不孬的测试程序
     *          agcdef
     *          这是一个不孬的测试程序
     *          这是一个不孬的测试程序
     *          agcdef
     *          这是一个不孬的测试程序
     *          这是一个不孬的测试程序
     *          agcdef
     *          agcdef
     *          agcdef
     *          这是一个不孬的测试程序
     * 】
     *  而不是【agc这是d一个】的效果，就说明是按照顺序执行的，也就是说，每个线程的执行【写入管道，输出到具体文件】都是完整的，原子性的，也就是同步的.
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_03() throws IOException, InterruptedException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\ttt.txt"));
        FileChannel fileChannel = fileOutputStream.getChannel();

        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(() -> {
                ByteBuffer byteBuffer = ByteBuffer.wrap("agcdef\r\n".getBytes());
                try {
                    int write = fileChannel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            Thread t2 = new Thread(() -> {
                ByteBuffer byteBuffer = ByteBuffer.wrap("这是一个不孬的测试程序\r\n".getBytes());
                try {
                    int write = fileChannel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println(String.format("线程t1-id：%s", t1.getId()));
            System.out.println(String.format("线程t2-id：%s", t2.getId()));

            t1.start();
            t2.start();

        }
        Thread.sleep(3000);
        fileChannel.close();
        fileOutputStream.close();
    }

    /**
     * 测试fileChannel.write(bytebuffer): 在写入通道时，如何读取缓冲区 remaining 的
     *
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\tt.txt"));
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer1 = ByteBuffer.wrap("abcde".getBytes());
        ByteBuffer byteBuffer2 = ByteBuffer.wrap("12345".getBytes());
        System.out.println(byteBuffer2.limit());
        fileChannel.write(byteBuffer1);

        //设置通道的文件位置
        fileChannel.position(2);
        //设置缓冲区的 remaining，测试FileChannel的write方法在获取缓冲区的 remaining
        byteBuffer2.position(2);
        byteBuffer2.limit(5);
        fileChannel.write(byteBuffer2);

        fileChannel.close();
        fileOutputStream.close();
    }

    /**
     * 验证方法fileChannel.write(bytebuffer):从通道的当前位置开始写入
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_01() throws IOException, InterruptedException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\t.txt"));
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.wrap("abcde".getBytes());
        System.out.println(String.format("A filechannel position=%s", fileChannel.position()));
        //fileChannel.write(buffer):将buffer中的remaing中的字节序列写入通道中，返回写入的字节数，并且write方法是同步的；
        System.out.println(String.format("B filechannel write() - 1 返回值 =%s", fileChannel.write(byteBuffer)));
        //fileChannel.position()：返回通道的文件位置
        System.out.println(String.format("C filechannel position=%s", fileChannel.position()));
        //fileChannel.position(2)：设置通道文件位置
        fileChannel.position(2);
        //还原buffer的position为0，然后在当前位置进行再次写入
        byteBuffer.rewind();
        System.out.println(String.format("D filechannel write() - 2 返回值 =%s", fileChannel.write(byteBuffer)));
        System.out.println(String.format("E  filechannel position=%s", fileChannel.position()));

        fileChannel.close();
        fileOutputStream.close();
    }
}

