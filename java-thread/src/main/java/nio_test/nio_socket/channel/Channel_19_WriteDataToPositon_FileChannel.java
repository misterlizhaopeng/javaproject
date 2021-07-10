package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_19_WriteDataToPositon_FileChannel
 * @Deacription : 向通道指定位置写数据
 *                  write(ByteBuffer src,long position) 把缓冲区的 remaining 的字节序列写入通道的指定位置；
 *                  src:表示字节的缓冲区，position 代表开始传输的文件位置；
 *                  除了从给定的文件位置开始写入字节，而不是从该通道的当前位置外，此方法的执行方式与write(bytebuffer)方法相同；
 *                  注意：[[此方法不修改通道的位置]]！！
 *                  如果给定的位置大于该文件的大小，则该文件将扩大以容纳新的字节；在以前文件末尾 和 新写入字节之间的字节值是未指定的；
 *
 *              1.测试：从通道的指定位置开始写入
 *              2. 测试：把缓冲区的remaining 的内容写入通道
 *              3.测试：write(Bytebuffer src,long position) 方法的同步性
 *              4.测试：write(ByteBuffer src,long position) 方法position的位置（也是绝对位置），不改变通道的位置
 *
 *
 * @Author LP
 * @Date 2021/7/8
 * @Version 1.0
 **/
public class Channel_19_WriteDataToPositon_FileChannel {

    /**
     * 测试：write(ByteBuffer src,long position) 方法position的位置（也是绝对位置），不改变通道的位置
     * 测试结果：通道的位置确实不变，所以long position 表示文件的位置，也就是在通过通道向文件写入字节流的时候，position代表文件的位置
     * @throws IOException
     */
    @Test
    public void test_04() throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("write_position_no_change.txt")));
        //获取输出流对象的通道对象
        FileChannel fileChannel = fileOutputStream.getChannel();
        System.out.println(String.format("A position:%s",fileChannel.position()));
        fileChannel.position(3);
        System.out.println(String.format("B position:%s",fileChannel.position()));
        fileChannel.write(ByteBuffer.wrap("abcde".getBytes()),10);//向通道中写入字节流，从文件的10位置开始
        System.out.println(String.format("C position:%s",fileChannel.position()));
        fileOutputStream.close();
        fileChannel.close();
    }

    /**
     * 测试：write(Bytebuffer src,long position) 方法的同步性
     * 结果：就是哪个线程最后执行，就会在文件中写入那个线程的内容
     *
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException, InterruptedException {
        //创建输出文件流对象
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("write_position_synchronized.txt")));
        // 通过文件输出流对象获取通道对象
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建第一个线程
        Thread t1=new Thread(()->{
            System.out.println("线程1运行");
            ByteBuffer byteBuffer = ByteBuffer.wrap("12345".getBytes());
            try {
                fileChannel.write(byteBuffer,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //创建第二个线程
        Thread t2=new Thread(()->{
            System.out.println("线程2运行");
            //缓冲区
            ByteBuffer byteBuffer = ByteBuffer.wrap("abcde".getBytes());
            try {
                fileChannel.write(byteBuffer,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(3000);

        fileOutputStream.close();
        fileChannel.close();


    }
    /**
     * 测试：把缓冲区的remaining 的内容写入通道
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("write_position_buf_remaining.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.wrap("abcdef".getBytes());
        byteBuffer.position(2);
        byteBuffer.limit(4);//remaining 为：cd
        int writeLen = fileChannel.write(byteBuffer, 0);
        System.out.println(String.format("写入通道的字节数量为：%s", writeLen));

        //关闭写入文件流
        fileOutputStream.close();
        //关闭通道
        fileChannel.close();
    }

    /**
     *
     * 测试：从通道的指定位置开始写入
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
        writeLen = fileChannel.write(byteBuffer, 2);//从通道指定第2个位置开始写入字节序列
        System.out.println(String.format("B 再次写入通道的字节长度为：%s",writeLen));

        //关闭通道资源
        fileChannel.close();
        //关闭输出字节流资源
        fileOutputStream.close();

    }
}

