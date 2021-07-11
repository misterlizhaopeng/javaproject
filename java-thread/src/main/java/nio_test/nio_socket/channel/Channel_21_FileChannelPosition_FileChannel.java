package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_21_FileChannelPosition_FileChannel
 * @Deacription : 设置文件的位置
 *                  FileChannel.position(long position);//设置通道的位置
 *                  FileChannel.size();//表示通道文件的大小，单位字节
 *
 *                  注意：【使用FileOutputStream对象获取的文件通道，获取的文件大小为0，用 RandomAccessFile 对象获取的通道可以获取文件大小；】
 *
 *
 * @Author LP
 * @Date 2021/7/11
 * @Version 1.0
 **/
public class Channel_21_FileChannelPosition_FileChannel {
    /**
     *
     * 设置通道的位置不影响当前的大小，但会在下次写入内容的时候影响文件大小
     *
     * 如果设置的通道的 position 位置大于文件大小，不会影响文件大小但再次写入字节，会在position之后写入字节内容
     * 之前的文件内容和新写入的字节内容之间的区域是不指定内容的
     *
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        //测试默认的set_channel_position内容为ab123
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("set_channel_position.txt")),"rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        System.out.println(String.format("通道位置：%s,文件大小：%s",fileChannel.position(),fileChannel.size())); //通道位置：0,文件大小：5
        System.out.println(fileChannel.read(ByteBuffer.allocate(10),1000));//读取文件1000的位置，返回-1，表示没有读取任何内容
        //设置通道位置大于文件大小
        fileChannel.position(9);
        System.out.println(String.format("通道位置：%s,文件大小：%s",fileChannel.position(),fileChannel.size())); //通道位置：9,但是文件大小还是：5
        //设置了大于文件大小的通道位置9之后，再次写入一个字节
        fileChannel.write(ByteBuffer.wrap("a".getBytes()));
        System.out.println(String.format("通道位置：%s,文件大小：%s",fileChannel.position(),fileChannel.size())); //通道位置变为：10,文件大小变为：10

        randomAccessFile.close();
        fileChannel.close();





    }
    /**
     * 如何设置通道的文件位置
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("set_channel_position.txt")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        System.out.println(String.format("A FileChannel 的位置：%s，文件大小：%s",fileChannel.position(),fileChannel.size()));

        //声明两个缓冲区
        ByteBuffer b1 = ByteBuffer.wrap("abce".getBytes());
        ByteBuffer b2 = ByteBuffer.wrap("123".getBytes());

        fileChannel.write(b1);
        System.out.println(String.format("A FileChannel 的位置：%s，文件大小：%s",fileChannel.position(),fileChannel.size()));
        fileChannel.position(2);//设置通道位置，下面再次写入第二个缓冲区
        fileChannel.write(b2);
        System.out.println(String.format("A FileChannel 的位置：%s，文件大小：%s",fileChannel.position(),fileChannel.size()));

        //执行结果：写入了:ab123
        fileChannel.close();
        fileOutputStream.close();




    }
}

