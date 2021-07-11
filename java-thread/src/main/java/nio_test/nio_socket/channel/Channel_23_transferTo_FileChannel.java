package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_23_transferTo_FileChannel
 * @Deacription :    transferTo(long position,long count, WritableByteChannel target)：将数据传输到其他可写的通道中（简单来记：将通道中的数据传输到另一个通道）；
 *                   作用：读取此通道的position位置开始的文件count个字节数，并将其写入到目标通道的当前位置；
 *                   注意：此方法不一定传输所有字节数据，取决于通道的性质和状态。
 *                      如果此通道的文件从给定的position位置开始读取的字节数小于count，或者目标通道是非阻塞的并且其缓冲区的自由空间小于count个字节，则传输的字节数小于count；
 *
 *                  transferTo 相当于 write() 方法，只不过是通道传输到通道，而不是从缓冲区写入到通道。
 *
 *                  注意：此方法不修改通道的position！！
 *
 *                  如果给定的位置大于该文件的当前大小，则不传输任何数据；
 *
 *
 *                  1.如果当前位置大于此通道文件大小，则不传输数据
 *                  2.正常传输数据
 *                  3.如果count 个数大于position到size的数量，则传输 size-position 的个数字节序列到目标通道的当前位置!
 *
 * @Author LP
 * @Date 2021/7/11
 * @Version 1.0
 **/
public class Channel_23_transferTo_FileChannel {


    /**
     *
     * 如果count 个数大于position到size的数量，则传输size-position的个数字节序列到目标通道的当前位置!
     *  注意两种情况：count 与size-position的比较，谁小，传输较小数量的字节序列!
     *
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException {
        RandomAccessFile r1 = new RandomAccessFile(new File(FilePahtVar.getFile("transfer1")), "rw");
        RandomAccessFile r2 = new RandomAccessFile(new File(FilePahtVar.getFile("transfer2")), "rw");
        FileChannel fc1 = r1.getChannel();
        FileChannel fc2 = r2.getChannel();
        fc1.write(ByteBuffer.wrap("12345678".getBytes()));
        fc2.write(ByteBuffer.wrap("a-----5".getBytes()));
        System.out.println(String.format("目标的当前位置：%s，当前大小：%s",fc2.position(),fc2.size()));

        //fc2.position(fc2.size());// 此行代码表示两个方面的意思：1.transferTo方法不修改目标通道的当前位置 ；2.如果想要拿到文件的位置，用通道.size() 即可，这样就可以实现下面的【方式2】一直往目标通道追加字节序列；

        //long transLen = fc1.transferTo(0, 1000, fc2);//为size-position，因为count>size-position
        long transLen = fc1.transferTo(3, 2, fc2);//【方式2】为count，因为count<size-position
        System.out.println(String.format("传输的长度:%s",transLen));
        r1.close();
        r2.close();
        fc1.close();
        fc2.close();
    }

    /**
     *
     * 正常传输数据
     *
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        RandomAccessFile f1 = new RandomAccessFile(new File(FilePahtVar.getFile("transterTo_normal_1")), "rw");
        RandomAccessFile f2 = new RandomAccessFile(new File(FilePahtVar.getFile("transterTo_normal_2")), "rw");
        FileChannel fc1 = f1.getChannel();
        FileChannel fc2 = f2.getChannel();

        //写入文件
        fc1.write(ByteBuffer.wrap("12345678".getBytes()));
        fc2.write(ByteBuffer.wrap("abcdefgh".getBytes()));

        System.out.println(String.format("当前通道的位置：%s,当前通道文件大小：%s",fc1.position(),fc1.size()));
        System.out.println(String.format("目标通道的位置：%s,目标通道文件大小：%s",fc2.position(),fc2.size()));

        //正常转移

        fc1.transferTo(1,3,fc2);//234->fc2(内容为：abcdefgh234)
        f1.close();
        f2.close();
        fc1.close();
        fc2.close();
    }
    /**
     * 如果当前位置大于此通道文件大小，则不传输数据
     *
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        RandomAccessFile randomAccessFile1 = new RandomAccessFile(FilePahtVar.getFile("transferTo.txt"),"rw");
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(FilePahtVar.getFile("transfterTo2.txt"), "rw");
        //通道1
        FileChannel fileChannel1 = randomAccessFile1.getChannel();
        //通道2
        FileChannel fileChannel2 = randomAccessFile2.getChannel();

        fileChannel1.write(ByteBuffer.wrap("123456".getBytes()));
        fileChannel2.write(ByteBuffer.wrap("abcdefg".getBytes()));
        System.out.println(String.format("目标通道文件位置：%s,目标通道文件大小：%s",fileChannel2.position(),fileChannel2.size()));

        //如果当前位置大于此通道文件大小，则不传输数据
        long transferTo = fileChannel1.transferTo(1000, 1, fileChannel2);
        System.out.println(String.format("%s",transferTo));

        randomAccessFile1.close();
        randomAccessFile2.close();

        fileChannel1.close();
        fileChannel2.close();
    }
}

