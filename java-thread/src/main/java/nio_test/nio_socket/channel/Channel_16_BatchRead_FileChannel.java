package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_16_BatchRead_FileChannel
 * @Deacription : 批量从通道读取内容 : 实现接口 ScatteringByteChannel 的read方法
 *                  1.验证long read(bytebuffers ) 返回值的意义
 *                  2.从通道的当前位置开始读取
 *                  3.测试方法long read(bytebuffers) 将字节放入 bytebuffer 缓冲区的当前位置
 *                  4.测试long read(bytebuffers)方法的同步执行
 *                  5.测试通道的内容大于缓冲区集合的容量(结论：缓冲区结合总共的容量remaining大小为多少，就从通道中读取多少)
 *                  6.测试：从通道读取的字节放入缓冲区的 remaining 中
 * @Author LP
 * @Date 2021/7/3
 * @Version 1.0
 **/
public class Channel_16_BatchRead_FileChannel {


    /**
     * 测试：从通道读取的字节放入缓冲区的remaining中
     * @throws IOException
     */
    @Test
    public void test_06() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(getFile("r1.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        //缓冲区集合
        ByteBuffer b1 = ByteBuffer.allocate(5);
        ByteBuffer b2 = ByteBuffer.allocate(5);

        b1.position(2);
        b1.limit(3);//b1 remaining 1
        b2.position(2);
        b2.limit(4);//b2 remaining 2

        ByteBuffer [] bs = new ByteBuffer[]{b1,b2};
        long readLen = fileChannel.read(bs);
        System.out.println(String.format("读取字节的长度：%s",readLen));
        printByteBuffers(bs);
        fileChannel.close();
        fileInputStream.close();
    }

    /**
     * 测试通道的内容大于缓冲区集合的容量
     * 结论：缓冲区结合总共的容量remaining大小为多少，就从通道中读取多少
     * @throws IOException
     */
    @Test
    public void test_05() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(getFile("r1.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        // 开辟缓冲区
        ByteBuffer b1 = ByteBuffer.allocate(2);
        ByteBuffer b2 = ByteBuffer.allocate(2);
        //缓冲区集合，从通道里面批量读取数据内容 到 缓冲区集合中；
        ByteBuffer[] bs =new ByteBuffer[]{b1,b2};
        long readLen = fileChannel.read(bs);
        System.out.println(String.format(" 从通道里面读取的长度：%s",readLen));
        printByteBuffers(bs);
        fileInputStream.close();
        fileChannel.close();
    }

    /**
     * 打印缓冲区的集合数据
     * @param bs
     */
    private void printByteBuffers(ByteBuffer [] bs) {
        for (int j = 0; j < bs.length; j++) {
            ByteBuffer byteBuffer=bs[j];
            byte[] array = byteBuffer.array();
            for (int k = 0; k <array.length; k++) {
                System.out.print((char)array[k]);
            }
            System.out.println();
        }
    }


    /**
     * 测试long read(bytebuffers)方法的同步执行(一组表示一个线程要读取的数据，一组读两行，从运行结果来看，没有乱序，表示 read 方法为同步方法)
     *  注意：bytebuffer 缓冲区添加换行\r\n占用两个字节 !!!!
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_04() throws IOException, InterruptedException {
        FileInputStream fileInputStream = new FileInputStream(new File(getFile("r_batch_thread_read.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();

        for (int i = 0; i < 10; i++) {
           new Thread(() -> {
                try {
                    ByteBuffer b1 = ByteBuffer.allocate(8);
                    ByteBuffer b2 = ByteBuffer.allocate(8);
                    ByteBuffer[] bs = new ByteBuffer[]{b1, b2};

                    long readLen = fileChannel.read(bs);
                    while (readLen != -1) {
                        //同步的目的：保证输出有序性
                        synchronized (Object.class) {
                            for (int j = 0; j < bs.length; j++) {
                                byte[] array =  bs[j].array();
                                for (int k = 0; k < array.length; k++) {
                                    System.out.print((char) array[k]);
                                }
                            }
                        }
                        b1.clear();//表示可以重新覆盖缓冲区的内容
                        b2.clear();
                        readLen = fileChannel.read(bs);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        Thread.sleep(3000);
        fileInputStream.close();
        fileChannel.close();
    }

    /**
     * 测试方法long read(bytebuffers) 将字节放入 bytebuffer 缓冲区的当前位置
     *
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException {
        //读文件对象
        FileInputStream fileInputStream = new FileInputStream(new File(getFile("rrr.txt")));
        //操作该文件的通道
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer b1 = ByteBuffer.allocate(3);
        ByteBuffer b2 = ByteBuffer.allocate(3);
        ByteBuffer[] bs = {b1, b2};
        //设置缓冲区的当前位置
        b1.position(2);
        fileChannel.read(bs);
        printByteBuffers(bs);
        fileInputStream.close();
        fileChannel.close();


    }

    /**
     * 从通道的当前位置开始读取
     *
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(getFile("rr.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        //设置通道的位置
        fileChannel.position(2);
        ByteBuffer b1 = ByteBuffer.allocate(2);
        ByteBuffer b2 = ByteBuffer.allocate(2);
        ByteBuffer[] bs = {b1, b2};
        //从通道中的当前位置开始读取数据，到缓冲区集合中；
        fileChannel.read(bs);
        //遍历缓冲区集合
        printByteBuffers(bs);
        fileInputStream.close();
        fileChannel.close();


    }

    /**
     * 验证long read(bytebuffers ) 返回值的意义；返回读取的字节数
     *
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
        ByteBuffer[] bs = {b1, b2};
        long readLen = fileChannel.read(bs);
        System.out.println(String.format("a 取得字节数量为：%s", readLen));//4
        b1.clear();
        b2.clear();

        readLen = fileChannel.read(bs);
        System.out.println(String.format("b 取得字节数量为：%s", readLen));//1
        b1.clear();
        b2.clear();

        readLen = fileChannel.read(bs);
        System.out.println(String.format("b 取得字节数量为：%s", readLen));//-1
        b1.clear();
        b2.clear();

        fileChannel.close();
        fileInputStream.close();
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

