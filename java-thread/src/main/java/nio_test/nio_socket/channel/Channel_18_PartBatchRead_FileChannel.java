package nio_test.nio_socket.channel;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_17_PartBatchRead_FileChannel
 * @Deacription : 部分批量读通道：将通道中当前位置的字节序列读入以下标为 offset
 *                              开始的缓冲区集合的 remaining 区域中，并且连续写入 length 个冲区缓；
 *
 *                      1.测试方法read(bytebuffers,offset,length) 返回值的意义
 *                      2.测试将通道的内容放入缓冲区的当前位置
 *                      3.测试通道的有序性
 *                      4.从通道读取的数据长度大于缓冲区的remaining的情况:这种情况，实际上是按照缓冲区的remaining的大小进行读取通道内容的；
 * @Author LP
 * @Date 2021/7/6
 * @Version 1.0
 **/
public class Channel_18_PartBatchRead_FileChannel {


    /**
     * 从通道读取的数据长度大于缓冲区的remaining的情况: 这种情况，实际上是按照缓冲区的remaining的大小进行读取通道内容的；
     * @throws IOException
     */
    @Test
    public void test_04() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(FilePahtVar.getFile("r_p.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();

        //声明通道集合
        ByteBuffer b1 = ByteBuffer.allocate(6);
        //读取通道的内容到缓冲区的remaining中；
        b1.position(4);
        b1.limit(5);
        ByteBuffer b2 = ByteBuffer.allocate(1);
        ByteBuffer bs [] = new ByteBuffer[]{b1,b2};
        //读取通道
        long readnLen = fileChannel.read(bs, 0, 2);
        //打印内容
        for (int k = 0; k < bs.length; k++) {
            byte[] array = bs[k].array();
            for (int j = 0; j < array.length; j++) {
                System.out.print((char)array[j]);
            }
            //换行
            System.out.println();
        }

        //关闭文件流
        fileInputStream.close();
        //关闭通道
        fileChannel.close();
    }

    /**
     * 测试通道的有序性：
     * 文件内容如下，每次读取两行，放入两个缓冲区，不会出现交错的情况，说明读取通道是有效的
     *  123456
     *  abcdef
     *  555555
     *  654321
     *  789012
     *  llllll
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException, InterruptedException {
        FileInputStream fileInputStream = new FileInputStream(new File(FilePahtVar.getFile("r_p_t.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        //多线程读取通道
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                //声明缓冲区集合
                ByteBuffer b1 = ByteBuffer.allocate(8);
                ByteBuffer b2 = ByteBuffer.allocate(8);
                ByteBuffer bs[]=new ByteBuffer[]{b1,b2};
                try {
                    long readLen = fileChannel.read(bs, 0, 2);
                    System.out.println(String.format("读取字节长度：%s",readLen));
                    //打印当前线程读取的内容
                    System.out.println(String.format("线程名称：%s",Thread.currentThread().getName()));
                    for (int k = 0; k < bs.length; k++) {
                        byte[] array = bs[k].array();
                        for (int j = 0; j < array.length; j++) {
                            System.out.print((char)array[j]);
                        }
                        //换行
                        System.out.println();
                    }
                    System.out.println(String.format("线程名称：%s",Thread.currentThread().getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        Thread.sleep(3000);

        //关闭文件流
        fileInputStream.close();
        //关闭通道
        fileChannel.close();
    }

    /**
     * 测试将通道的内容放入缓冲区的当前位置
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(FilePahtVar.getFile("r_p.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();

        //声明缓冲区集合
        ByteBuffer b1 = ByteBuffer.allocate(8);
        ByteBuffer b2 = ByteBuffer.allocate(8);
        ByteBuffer[] bs =new ByteBuffer[]{b1,b2};
        // 设置缓冲区的当前位置
        b1.position(2);
        b1.limit(3);
        b2.position(5);
        b2.limit(6);

        //读取通道的内容，放入缓冲区结合的当前位置
        long readLen = fileChannel.read(bs, 0, 2);
        System.out.println(String.format("读取的字节长度：%s",readLen));

        //打印读取的内容
        for (int k = 0; k < bs.length; k++) {
            byte[] array = bs[k].array();
            for (int l = 0; l < array.length; l++) {
                System.out.print((char)array[l]);;
            }
            //换行：一个数组打印一行
            System.out.println();
        }

        //关闭输入文件流
        fileInputStream.close();
        //关闭通道
        fileChannel.close();
    }

    /**
     * 测试方法read(bytebuffers,offset,length) 返回值的意义：
     * 结论：按照缓冲区集合的总大小（总的remaining的大小）进行从通道的当前位置开始读取
     */
    @Test
    public void test_01() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(FilePahtVar.getFile("r_p.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        //设置通道的当前位置(读取通道字节数据，就是从通道的当前位置开始读取)
        fileChannel.position(1);
        //声明缓冲区集合
        ByteBuffer b1 = ByteBuffer.allocate(2);
        ByteBuffer b2 = ByteBuffer.allocate(2);
        ByteBuffer[] bs  =new ByteBuffer[]{b1,b2};
        long readLen = fileChannel.read(bs, 0, 2);
        System.out.println(String.format("A 读取的字节数：%s",readLen));

        //重新整理缓冲区集合的操作位置
        b1.clear();
        b2.clear();
        readLen = fileChannel.read(bs, 0, 2);
        System.out.println(String.format("B 读取的字节数：%s",readLen));

        //重新整理缓冲区集合的操作位置
        b1.clear();
        b2.clear();
        readLen = fileChannel.read(bs, 0, 2);
        System.out.println(String.format("C 读取的字节数：%s",readLen));

        //关闭输入文件流
        fileInputStream.close();
        //关闭通道
        fileChannel.close();

        //结论：按照缓冲区集合的总大小（总的remaining的大小）进行从通道的当前位置开始读取
    }
}

