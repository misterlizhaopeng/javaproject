package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_25_lock_FileChannel
 * @Deacription : FileLock lock(long position, long size, boolean shared):获取此通道的文件指定区域上的锁。
 *                      在可以锁定通道之前，已关闭此通道之前或者已中断调用此线程之前（以先到者为准），将阻塞此方法调用。
 *
 *                      在此方法调用期间，另一个线程关闭了通道，抛异常：AsynchronousCloseException（If another thread closes this channel while the invoking thread is blocked in this method）；
 *                      如果在等待获取锁的同时中断了调用线程，将状态设置为中断并抛出异常（FileLockInterruptionException）；
 *                      如果调用此方法时，已设置调用方的中断状态，则立即抛出该异常，不更改线程的中断状态；
 *
 *                      由position 和 size 参数指定的区域无须包含在实际的文件中，甚至无须与文件重叠。锁定的区域大小使固定的；如果某个锁定的区域最初包含整个文件，因文件扩大超出锁定区域，则该锁定不覆盖新的内容部分（如果期望文件扩大的同时并锁定整个文件，则position为0，size 应该传入大于或者预计文件大小的值）；
 *                      没有参数的lock()方法，只是锁定大小为Long.MAX_VALUE的区域；
 *
 *                      文件锁定，要么是独占，要么是共享。
 *
 *                      某些操作系统不支持共享锁，这种情况下，java代码底层会把共享锁自动变为互斥锁。可通过isShare()方法来获取是共享锁还是独占锁。
 *
 *                      文件锁定是以jvm虚拟机来保持的。但他们不适用于一个jvm虚拟机下的多个线程对文件的访问。
 *
 *
 *
 *
 *                      1.测试：FileLock lock()方法是同步的【 测试方法1 和 测试方法2 】
 *                      2.验证：在此方法调用期间，另一个线程关闭了通道 的情况下，抛异常：AsynchronousCloseException
 *                      3.当前线程在获取锁的过程中，别的线程中断了当前线程，抛出异常：java.nio.channels.FileLockInterruptionException
 *                      4.测试: 当前线程 获取了[共享锁]之后，自己就不能对文件进行写操作：简单总结：共享锁自己不能写
 *                      5.测试：当前线程 获取了[共享锁]之后，别人就不能对文件进行写操作：简单总结：共享锁别人不能写
 *                      6.测试：当前线程 获取了[共享锁]之后，自己能对文件进行读操作：简单总结：共享锁自己能读
 *                      7.测试：当前线程 获取了[共享锁]之后，别人能对文件进行读操作：简单总结：共享锁别人能读
 *                      小结：共享锁是只读的
 *
 *                      8.测试：当前线程 获取了[独占锁]之后，自己能对文件进行写操作：简单总结：独占锁自己能写
 *                      9.测试：当前线程 获取了[独占锁]之后，别人不能对文件进行写操作：简单总结：独占锁别人不能写
 *                      10.测试：当前线程 获取了[独占锁]之后，自己能对文件进行读操作：简单总结：独占锁自己能读
 *                      11.测试：当前线程 获取了[独占锁]之后，别人不能对文件进行读操作：简单总结：独占锁别人不能读
 *
 *
 *
 *
 *
 * @Author LP
 * @Date 2021/7/12
 * @Version 1.0
 **/
public class Channel_25_lock_FileChannel {
    /**
     * 测试：当前线程 获取了[独占锁]之后，别人不能对文件进行读操作
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_011_2() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_011_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        fileChannel.read(byteBuffer);

        byte[] array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print((char)array[i]);
        }
    }
    /**
     * 测试：当前线程 获取了[独占锁]之后，别人不能对文件进行读操作
     * @throws IOException
     */
    @Test
    public void test_011_1() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_011_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        fileChannel.write(ByteBuffer.wrap("123456".getBytes()));//init 6 char
        fileChannel.lock(0,fileChannel.size(),false);//加上独占锁
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 测试：当前线程 获取了[独占锁]之后，自己能对文件进行读操作
     * @throws IOException
     */
    @Test
    public void test_010() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_010")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        int writeLen = fileChannel.write(ByteBuffer.wrap("abedefg".getBytes()));
        System.out.println(String.format("init length is:%s", writeLen));

        fileChannel.lock(0,fileChannel.size(),false);//给当前线程加上独占锁

        //接着读取放到缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(String.format("bytebuffer's position:%s,capacity:%s,limit:%s",byteBuffer.position(),byteBuffer.capacity(),byteBuffer.limit()));

        System.out.println(String.format("fileChannel's position:%s,size:%s",fileChannel.position(),fileChannel.size()));
        //从通道的0位置开始读取字节序列放入缓冲区中
        int readLen = fileChannel.read(byteBuffer,0);
        System.out.println(String.format("读取的内容长度为：%s", readLen));
        byte[] array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.println((char)array[i]);
        }

    }



    /**
     *  测试：当前线程 获取了[独占锁]之后，别人不能对文件进行写操作；测试过程中，独占锁锁定的只是一个范围，在别的进程下面的线程写非锁定的空间范围是不受锁的限制的
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_09_2() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_09_2")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        fileChannel.position(6);
        fileChannel.write(ByteBuffer.wrap("new Msg".getBytes()));
    }
    /**
     * 测试：当前线程 获取了[独占锁]之后，别人不能对文件进行写操作；测试过程中，独占锁锁定的只是一个范围，在别的进程下面的线程写非锁定的空间范围是不受锁的限制的
     *
     * @throws IOException
     */
    @Test
    public void test_09_1() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_09_2")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        //init file content
        fileChannel.write(ByteBuffer.wrap("abc".getBytes()));
        fileChannel.lock(1,fileChannel.size(),false);//获取独占锁
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 测试：当前线程 获取了[独占锁]之后，自己能对文件进行写操作
     * @throws IOException
     */
    @Test
    public void test_08() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_08")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        //init file content
        fileChannel.write(ByteBuffer.wrap("abc".getBytes()));

        fileChannel.lock(0,fileChannel.size(),false);//获取独占锁
        //写操作
        int writeLen = fileChannel.write(ByteBuffer.wrap("defg".getBytes()));
        System.out.println(String.format("%s",writeLen));

    }


    /**
     * 测试：当前线程 获取了[共享锁]之后，别人能对文件进行读操作
     * @throws IOException
     */
    @Test
    public void test_07_2() throws IOException, InterruptedException {

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_07_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        fileChannel.read(byteBuffer);
        byte[] array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.println((char)array[i]);//可以读到内容
        }
    }

    /**
     * 测试：当前线程 获取了[共享锁]之后，别人能对文件进行读操作
     * @throws IOException
     */
    @Test
    public void test_07_1() throws IOException, InterruptedException {

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_07_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        fileChannel.write(ByteBuffer.wrap("ab".getBytes()));
        fileChannel.lock(0,fileChannel.size(),true);//加上共享锁
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 测试：当前线程 获取了[共享锁]之后，自己能对文件进行读操作
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_06() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_06")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        FileLock shareLock = fileChannel.lock(0, 5, true); //上共享锁
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        fileChannel.read(byteBuffer);
        //打印自己读取的内容的字节长度
        System.out.println(byteBuffer.capacity());//存在长度，说明测试成功
    }




    /**
     * 测试：当前线程 获取了[共享锁]之后，别人就不能对文件进行写操作
     * @throws IOException
     */
    @Test
    public void test_05_2() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_05_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        fileChannel.write(ByteBuffer.wrap("abc123".getBytes()));

    }
    /**
     * 测试：当前线程 获取了[共享锁]之后，别人就不能对文件进行写操作
     * @throws IOException
     */
    @Test
    public void test_05_1() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_05_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        fileChannel.lock(1,2,true);//获取共享锁，正在测试别的线程不能写
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     *
     * 测试:当前线程 获取了[共享锁]之后，自己就不能对文件进行写操作
     *
     * @throws IOException
     */
    @Test
    public void test_055() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_05")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock shareLock = fileChannel.lock(0, 2, true);//通过通道获取共享锁
        fileChannel.write(ByteBuffer.wrap("123456".getBytes()));//写操作
    }


    /**
     * 当前线程在获取锁的过程中，别的线程中断了当前线程，抛出异常：java.nio.channels.FileLockInterruptionException
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_04() throws IOException, InterruptedException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("lock_c")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        //向文件中写入一点内容
        fileChannel.write(ByteBuffer.wrap("content".getBytes()));

        Thread t1 = new Thread(()->{
            try {
                for (int i = 0; i < 1; i++) {
                    System.out.println(i);
                }
                fileChannel.lock(1,2,false);
            }  catch (IOException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        //当t1线程获取lock的过程中，主线程把t1线程中断了，就会抛异常：java.nio.channels.FileLockInterruptionException
        t1.interrupt();//中断当前线程

        //关闭资源
        //fileChannel.close();
        //fileOutputStream.close();
    }



    /**
     * 验证：在此方法调用期间，另一个线程关闭了通道 的情况下，抛异常：AsynchronousCloseException
     *
     * 当前代码：出现异常具有随机性，因为多线程启动以及关闭时机有cpu决定,反过来说:一旦出现了异常：AsynchronousCloseException ，说明在执行lock 时，另一个线程对通道执行了 close 操作；
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_03() throws IOException, InterruptedException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(FilePahtVar.getFile("lock_b")));
        FileChannel fileChannel = fileOutputStream.getChannel();
        //先往文件中写入部分内容
        int write = fileChannel.write(ByteBuffer.wrap("abcdef".getBytes()));
        Thread t1=new Thread(()->{
            try {
                fileChannel.lock(1,2,false);//获取独占锁
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread t2 =new Thread(()->{
            try {
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //验证：在此方法调用期间，另一个线程关闭了通道 的情况下，抛异常：AsynchronousCloseException
        //获取锁
        t1.start();
        Thread.sleep(1);
        //关闭所
        t2.start();
        Thread.sleep(1000);
        fileChannel.close();
        fileOutputStream.close();
    }





    /**
     *
     * 测试：FileLock lock()方法是同步的
     * 本实验需要两个进程，所以需要启动两个java进程进行测试，一个单元测试，对应一个进程：
     *      两个进程都对文件lock_a进行加了独占锁，两段代码思路一致，所以一个进程加了独占锁，另一个进程就不能获取到独占锁了(现状就是出现了阻塞)，只有上一个进程放弃了独占锁，另一个进程才能获取锁，不论先执行那个单元测试方法；
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_01() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("lock_a")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        System.out.println("A begin");
        fileChannel.lock(1,2,false);// 独占锁
        System.out.println("A end");
        Thread.sleep(Integer.MAX_VALUE);
        randomAccessFile.close();
        fileChannel.close();
    }
    @Test
    public void test_02() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("lock_a")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        System.out.println("B begin");
        FileLock lock = fileChannel.lock(1, 2, false);// 独占锁
        System.out.println("B end");
        Thread.sleep(Integer.MAX_VALUE);
        randomAccessFile.close();
        fileChannel.close();

    }





}


