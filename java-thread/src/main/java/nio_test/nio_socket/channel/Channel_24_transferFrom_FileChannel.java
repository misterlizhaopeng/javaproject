package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_24_transferFrom_FileChannel
 * @Deacription : transferFrom(ReadableByteChannel src,long position,long count) 方法是将字节从给定的通道传输到另一个通道，功能相当于 read()方法，但不是读取通道内容到缓冲区；
 *                  从原通道读取最多 count 个字节，写入此通道文件的position 处的位置；
 *                  注意：此方法不一定传输所有字节数据，取决于通道的性质和状态。
 *
 *
 * @Author LP
 * @Date 2021/7/11
 * @Version 1.0
 **/
public class Channel_24_transferFrom_FileChannel {

    /**
     * 如果count 大于src.remaining,则把src.remaining 的字节传输到通道，否则（小于或者等于），传输count个字节
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException {
        RandomAccessFile r1 = new RandomAccessFile(new File(FilePahtVar.getFile("transferFrom1")), "rw");
        RandomAccessFile r2 = new RandomAccessFile(new File(FilePahtVar.getFile("transferFrom2")), "rw");
        FileChannel fc1 = r1.getChannel();
        FileChannel fc2 = r2.getChannel();
        fc1.write(ByteBuffer.wrap("12345678".getBytes()));//初始化内容
        fc2.write(ByteBuffer.wrap("abcdef".getBytes()));

        fc2.position(0);
        //long readLen = fc1.transferFrom(fc2, 0, 200);//count >src.remaining  结果：abcdef78
        long readLen = fc1.transferFrom(fc2, 0, 3);//count<src.remaining  结果：abc45678
        System.out.println(String.format("%s",readLen));



    }

    /**
     * 正常传输数据
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        RandomAccessFile r1 = new RandomAccessFile(new File(FilePahtVar.getFile("aa")), "rw");
        RandomAccessFile r2 = new RandomAccessFile(new File(FilePahtVar.getFile("bb")), "rw");

        FileChannel fc1 = r1.getChannel();
        FileChannel fc2 = r2.getChannel();

        //初始化数据
        fc1.write(ByteBuffer.wrap("123456".getBytes()));
        fc2.write(ByteBuffer.wrap("abcdef".getBytes()));

        fc2.position(4);
        long readLen = fc1.transferFrom(fc2, 3, 2);//把通道二的两个字节的内容“ef”放到通道fc1的第三个位置，覆盖两字节，结果为：123ef6
        System.out.println(String.format("%s",readLen));
        r1.close();
        r2.close();
        fc1.close();
        fc2.close();

    }

    /**
     * 测试: 如果给定的文件位置大于文件大小，则不传输任何字节;
     *      什么是给定文件大小，就是transferFrom的第二个参数值大于文件大小，不传输数据
     * 注意：与transferTo不同的是，transferFrom 不能使通道对应的文件大小增长
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        RandomAccessFile fileA = new RandomAccessFile(new File(FilePahtVar.getFile("a")), "rw");
        RandomAccessFile fileB = new RandomAccessFile(new File(FilePahtVar.getFile("b")), "rw");
        FileChannel fa = fileA.getChannel();
        FileChannel fb = fileB.getChannel();


        //初始化文本内容
        fa.write(ByteBuffer.wrap("abcdefg".getBytes()));
        fb.write(ByteBuffer.wrap("123456789".getBytes()));

        fb.position(4);
        //从fb通道的第4个位置开始读取2个字节，放到通道fa对应的文件里，此时100大于fa文件大小，不传输任何数据，因为transferFrom不能使通道对应的文件大小自动增长；
        long readLen = fa.transferFrom(fb, 100, 2);
        System.out.println(String.format("%s",readLen));

        fileA.close();
        fileB.close();

        fa.close();
        fb.close();
    }
}

