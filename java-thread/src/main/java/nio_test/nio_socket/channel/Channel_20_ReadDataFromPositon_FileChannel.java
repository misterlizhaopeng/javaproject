package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_20_ReadDataFromPositon_FileChannel
 * @Deacription :读取通道的指定位置
 *                  read(ByteBuffer dst,long position) 将通道的指定位置的字序列读取到缓冲区的当前位置，dst 代表缓冲区，position 代表开始传输的文件位置，不能为负数，负数也没意义；
 *                  除了从给定文件位置开始读取，不是从通道的当前位置之外，此方法执行方式与read(ByteBuffer)方法相同；
 *                  注意：[[此方法不修改通道的位置]]！！
 *                  如果给定的position 的位置大于文件的当前大小，不读取任何内容；
 *
 *
 *                  1.测试方法read(ByteBuffer dst,long position)方法返回值的意义
 *                  2.测试方法：read(ByteBuffer dst,long position) 读取文件内容到缓冲区的当前位置
 *                  3.测试方法：read(ByteBuffer dst,long position) 同步性
 *                  4.
 *
 * @Author LP
 * @Date 2021/7/10
 * @Version 1.0
 **/
public class Channel_20_ReadDataFromPositon_FileChannel {

    /**
     * 测试方法：read(ByteBuffer dst,long position) 同步性
     *
     * 这个测试证明了 RandomAccessFile对象获取的通道对象FileChannel的读写是按照顺序执行的！！
     * 下面先启动线程1（读线程），然后在启动写线程，用记事本打开被写的内容，每次都会写入 内容：aaaaaaaa这几个字节序列
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException, InterruptedException {

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("java_source_src.zip")),"rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        //声明缓冲区
        //ByteBuffer byteBuffer = ByteBuffer.allocate((int) (1024*1024*1));//1M大小
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) (21259296));//20.2M
        //读线程
        Thread t1 = new Thread(()->{
            try {
                fileChannel.read(byteBuffer,0);// 从文件的0位置开始读取字节流内容到缓冲区
                System.out.println(String.format("end thread1 ，当前时间为：%s",System.currentTimeMillis()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //写线程
        Thread t2= new Thread(()->{
            try {
                fileChannel.write(ByteBuffer.wrap("aaaaaaaa".getBytes()),fileChannel.size()+1);//fileChannel.size() 代表通道大小，亦文件大小
                System.out.println(String.format("end thread2 ，当前时间为：%s",System.currentTimeMillis()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println(String.format("begin time:%s",System.currentTimeMillis()));
        t1.start();
        Thread.sleep(100);
        t2.start();


//        fileChannel.close();
//        randomAccessFile.close();


    }
    /**
     * 测试方法：read(ByteBuffer dst,long position) 读取文件内容到缓冲区的当前位置
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(FilePahtVar.getFile("read_position_back_content.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        byteBuffer.position(2);//设置缓冲区的当前位置：第三个位置，因为缓冲区的位置从0开始索引的
        int readLen = fileChannel.read(byteBuffer, 0);
        System.out.println(String.format("读取字节的数量为:S%s",readLen));//读取字节的数量

        //打印缓冲区的内容
        byte[] array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print((char)array[i]);
        }


    }

    /**
     *  测试方法read(ByteBuffer dst,long position)方法返回值的意义：
     *
     *  小结：
     *  1.从文件的position位置开始读取，大小为缓冲区大小（亦缓冲区的remaining）的字节序列，并放入缓冲区 remaining 的空间区域
     *  2.如果position大于文件的大小，返回-1，且不读取任何内容；
     *
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(FilePahtVar.getFile("read_position_back_content.txt")));
        //通过输入文件流对象获取文件通道对象
        FileChannel fileChannel = fileInputStream.getChannel();

        //声明一个具有一定大小的空缓冲区对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        //设置缓冲区的remaining，
        byteBuffer.position(1);
        byteBuffer.limit(3);
        int readLen = fileChannel.read(byteBuffer, 2);//从文件的第2个位置开始读取缓冲区remaining大小的内容，并放入缓冲区的remaining 空间区域，因为文件内容为abcde，所以把cd读取到缓冲区了，从这里也能看出来，文件的开始位置也是从0索引的；
        System.out.println(String.format("A %s",readLen));//去读了2个字节

        //重新规整缓冲区
        byteBuffer.clear();

        readLen = fileChannel.read(byteBuffer, 10);
        System.out.println(String.format("B %s",readLen));//到达了文件的末尾值 -1，所以此时没有从文件中读取任何内容，因为10这个位置大于文件的大小，不读取任何内容！！

        //打印缓冲区的内容 - start
        byte[] array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print((char)array[i]);
        }
        //打印缓冲区的内容 - end

        byteBuffer.clear();

        fileInputStream.close();
        fileChannel.close();
    }
}

