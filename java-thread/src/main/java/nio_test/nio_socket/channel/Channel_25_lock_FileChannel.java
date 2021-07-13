package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

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
 *                      4...
 *
 * @Author LP
 * @Date 2021/7/12
 * @Version 1.0
 **/
public class Channel_25_lock_FileChannel {
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

