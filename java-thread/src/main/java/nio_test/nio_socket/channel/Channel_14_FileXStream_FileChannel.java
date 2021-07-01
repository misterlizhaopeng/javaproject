package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_14_FileXStream_FileChannel
 * @Deacription :   write: ( 1,2,3 );read(4,)
 *                      1.验证通道write方法是从当前位置开始写入
 *                      2.测试fileChannel.write(bytebuffer): 在写入通道时，如何读取缓冲区 remaining 的
 *                      3.测试filechannel的write方法是同步的（多线程下是同步的，也就是一个线程做完独立的任务之后，再由下一个线程执行操作）
 *
 *                      4.测试FileChannel方法read返回值的意义；
 *                      5.测试从通道的当前位置开始读取
 *                      6.
 * @Author LP
 * @Date 2021/6/29
 * @Version 1.0
 **/
public class Channel_14_FileXStream_FileChannel {

    /**
     * 测试从通道中读取字节到缓冲区的指定位置
     * @throws IOException
     */
    @Test
    public void test_06() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(getFile("r1.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        fileChannel.position(2);//设置通道的位置，之前的值不能被读取，只能从当前位置开始读取，所以，a，b 没有被读取出来
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.position(3);//设置缓冲区的位置（remaining还剩5-3=2个位置，那么从通道中读取的三个字节的前两个，放到缓冲区中）
        int readLen = fileChannel.read(byteBuffer);

        System.out.println(String.format("读取的字节长度:%s", readLen));

        byte[] array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print((char)array[i]);
        }
        fileChannel.close();
        fileInputStream.close();
    }

    /**
     * 测试从通道的当前位置开始读取
     * @throws IOException
     */
    @Test
    public void test_05() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(getFile("r1.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        fileChannel.position(2);//设置通道的位置，之前的值不能被读取，只能从当前位置开始读取，所以，a，b 没有被读取出来
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        int readLen = fileChannel.read(byteBuffer);
        System.out.println(String.format("读取的字节长度:%s", readLen));

        byte[] array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print((char)array[i]);
        }
        fileChannel.close();
        fileInputStream.close();
    }

    /**
     * read(ByteBuffer):测试通道read的方法的返回值的意义。
     * 该返回从通道中读取的字节数，读到最后为-1；
     */
    @Test
    public void test_04() throws IOException {

        FileInputStream fileInputStream = new FileInputStream(new File(getFile("r1.txt")));
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        int readLen = fileChannel.read(byteBuffer);//这里读取了两个字节，放入缓冲区
        System.out.println(String.format("读取的字节长度:%s", readLen));

        byteBuffer.clear();// 还原缓冲区的状态
        readLen = fileChannel.read(byteBuffer);//r1.txt 共五个字节，这里又读取了两个字节，又放入缓冲区
        byteBuffer.clear();// 还原缓冲区的状态
        readLen = fileChannel.read(byteBuffer);//r1.txt 共五个字节，这里有读取了r1.txt 的最后一个字节，并把最后一个字节放入缓冲区的第一个位置，此时把r1里面的五个字节读完了
        readLen = fileChannel.read(byteBuffer);//r1.txt 共五个字节，代表到达当前流的末端


        ByteBuffer byteBuffer1 = ByteBuffer.allocate(5);
        int read = fileChannel.read(byteBuffer1);//r1.txt 共五个字节，代表到达通道当前流的末端


        FileInputStream f2 = new FileInputStream(new File(getFile("r2.txt")));
        FileChannel f2Channel = f2.getChannel();
        ByteBuffer b2 = ByteBuffer.allocate(5);
        int read1 = f2Channel.read(b2);

        int r2 = f2Channel.read(b2);//r2.txt 共五个字节，上面已经读完，缓冲区没有位置了，返回为0
        int r3 = f2Channel.read(b2);//r2.txt 共五个字节，上面已经读完，缓冲区没有位置了，返回为0
        b2.clear();//还原缓冲区的状态
        int r4 = f2Channel.read(b2);//上句代码表示缓冲区的状态已经还原，此行代码再次读取，返回为-1，表示到达流的末端

        /*FileChannel read() 方法返回值的总结：
                    >0：表示从通道当前位置向缓冲区中读取的字节数；
                    0：表示从通道中没有读取任何数据，有可能发生的情况就是remaining没有空间了；
                    -1：表示到达流末端；
         */
    }


    /**
     * 测试filechannel的write方法是同步的
     * 文件结果：
     * 【
     * 这是一个不孬的测试程序
     * 这是一个不孬的测试程序
     * agcdef
     * 这是一个不孬的测试程序
     * agcdef
     * agcdef
     * agcdef
     * agcdef
     * 这是一个不孬的测试程序
     * 这是一个不孬的测试程序
     * agcdef
     * 这是一个不孬的测试程序
     * 这是一个不孬的测试程序
     * agcdef
     * 这是一个不孬的测试程序
     * 这是一个不孬的测试程序
     * agcdef
     * agcdef
     * agcdef
     * 这是一个不孬的测试程序
     * 】
     * 而不是【agc这是d一个】的效果，就说明是按照顺序执行的，也就是说，每个线程的执行【写入管道，输出到具体文件】都是完整的，原子性的，也就是同步的.
     *
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

