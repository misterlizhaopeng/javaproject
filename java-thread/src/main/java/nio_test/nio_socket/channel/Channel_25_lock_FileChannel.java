package nio_test.nio_socket.channel;

import ch.qos.logback.core.util.FileSize;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_25_lock_FileChannel
 * @Deacription : FileLock lock(long position, long size, boolean shared): 获取此通道的文件指定区域上的锁。
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
 *                      [个人体会]FileChannel 加锁，也可以说获取锁！！！！
 *
 *
 *
 *
 *                      1.测试：FileLock lock() 方法是同步的【 测试方法1 和 测试方法2 】
 *                      2.验证：在此方法调用期间，另一个线程关闭了通道 的情况下，抛异常：AsynchronousCloseException
 *                      3.当前线程在获取锁的过程中，别的线程中断了当前线程，抛出异常：java.nio.channels.FileLockInterruptionException
 *                      4.测试: 当前线程 获取了[共享锁]之后，自己就不能对文件进行写操作：简单总结：共享锁自己不能写
 *                      5.测试：当前线程 获取了[共享锁]之后，别人就不能对文件进行写操作：简单总结：共享锁别人不能写
 *                      6.测试：当前线程 获取了[共享锁]之后，自己能对文件进行读操作：简单总结：共享锁自己能读
 *                      7.测试：当前线程 获取了[共享锁]之后，别人能对文件进行读操作：简单总结：共享锁别人能读
 *                      小结：共享锁是只读的
 *                      8.测试：当前线程 获取了[独占锁]之后，自己能对文件进行写操作：简单总结：独占锁自己能写
 *                      9.测试：当前线程 获取了[独占锁]之后，别人不能对文件进行写操作：简单总结：独占锁别人不能写
 *                      10.测试：当前线程 获取了[独占锁]之后，自己能对文件进行读操作：简单总结：独占锁自己能读
 *                      11.测试：当前线程 获取了[独占锁]之后，别人不能对文件进行读操作：简单总结：独占锁别人不能读
 *                      小结：独占锁只能自己读写，别人不能读写操作
 *
 *                      12.验证lock方法参数position和size的含义
 *                      13.提前锁定
 *
 *                      14.测试：共享锁之间不是互斥关系
 *                      15.测试：共享锁/独占锁间是互斥关系
 *                              > 建立在14的基础上把test_014_2的锁变为独占锁，就不能正常获取独占锁了
 *                      16.独占锁与共享锁以及独占锁与独占锁之间都是护持关系；( 代码：略 )
 *                      17.FileLock lock();默认调用的是带参数的lock方法
 *                      18.各字节类型表示文件内容的最大值
 *                              Integer 可以表示最大的字节量为：1.9G，Long 可以表示的最大字节量为：八百八十多万 TB（具体为： 8388608 TB）
 *                      19.获取通道文件给定区域的锁定
 *                          tryLock()方法不会阻塞：无论是否成功获取锁，调用总是立即返回；
 *                              如果由于另一个程序保持着重叠锁定而无法获取锁，则此方法返回null，如果由于任何其他原因无法获取锁，则抛相应的异常；
 *                              position，size表示的范围无须在文件中真是存在，锁定区域是固定的；假如文件扩大，新增加的内容无法被原先锁定，如果要锁定，需要调整锁定范围；
 *                          某些操作系统不支持共享锁，可以通过FileLock.isShare()方法进行判断获取的锁是独占的还是共享的；
 *
 *                          文件锁定是以整个java 虚拟机来保持。但他们不适用于控制同一虚拟机内多个线程对文件的访问。(需要理解)
 *
 *                          tryLock(...) 、lock(...)区别：
 *                          tryLock(...) 非阻塞 而 lock(...) 是阻塞的，如果tryLock(...) 获取不到锁，返回 null
 *                          测试：tryLock(...) 为非阻塞的锁；
 *                      20.FileLock tryLock() 无参尝试锁定方法，底层调用的是tryLock(...)有参方法
 *                      21.FileLock 类的作用：
 *                          表示文件区间锁定的标志 ；锁定对象失效的三种方法: 调用锁定对象的 release() 方法，关闭通道或者关闭虚拟机；可以通过 FileLock.invalid() 方法判断 当前锁对象是否有效；
 *                          文件锁要么独占，要么共享，共享锁允许其他程序获取共享锁，大不能允许获取独占锁，而独占锁，不允许其他程序获取任和锁，当前锁一旦释放了，就不会对其他程序产生影响了；
 *                          isShare()方法判断当前锁的是共享还是独占，一些os不支持共享锁，会把共享锁自动转换为独占锁；
 *                          单个jvm 在保持某个文件上的锁定是不重叠的。要想测试某个锁定范围是否与现有锁重叠，可使用 overlaps() 方法；
 *                          文件锁定对象保持了锁定文件的通道、锁的类型和有效性，以及锁定区域的位置和大小。只有锁定有效性会随着时间改变，其他特性保持不变；
 *                          文件锁定以整个jvm虚拟机来保持。但他们不适用于控制同一个虚拟机内多个线程对文件的访问（需要理解）；
 *                          多个并发线程可以安全的使用文件锁定对象；
 *                          FileLock具有平台依赖性：就是说文件锁定对象锁定文件，是直接映射到os底层对文件进行锁定的，所以，这个锁对象对于其他任何程序都是可见的，体现锁特性；
 *                          由于某个锁定是否阻止另一个程序锁定访问该锁定区域的内容，是与os有关系的，因此是未指定的；不同的os存在不同的锁定特点：
 *                              劝告锁定、强制锁定，为了确保平台之间的统一和正确性，强烈建议：将此API提供的锁定作为劝告锁定来使用；
 *                          有些os上，在某个文件区域上获取 强制锁定 会阻止该区域 被映射到内存中，反之亦然（需要理解，是不是从内存中在弄到磁盘上也是被阻止的？？）。组合锁定和映射的程序应该为此组合的失败做好准备；
 *                          在有的os上，关闭通道会释放java虚拟机底层文件上所保持的所有锁定，而不管该锁定是当前通道打开的还是其他的通道打开的，强烈建议：在程序内使用唯一通道来获取当前文件的所有锁定（资料就是这个意思，应该没错，个人分析的）；
 *
 *                      22.FileChannel.force(boolean metaData);
 *                          强制对通道文件的更改写入设备中（比如磁盘）；以为FileChannel的 write 方法自动会把要写的内容先放到os内核缓存中，然后找到一个合适的时间点，再写到设备中，这样会出现内容写到设备出现延迟，force方法是强制把文件内容写到设备中；
 *                          metaData:true把文件内容和元数据更新大设备中，false只是把文件内容更新到设备中；
 *                          通过下面的测试代码，加上force方法调用之后，性能的确有下降；
 *
 *                      23.将通道文件区域直接映射到内存（可以理解为把文件直接映射为缓冲区）
 *                          MappedByteBuffer:map(MapMode mode,long position, long size): 将通道的文件区域映射到内存中,mode:映射方式，position:文件中的位置，size:映射文件区域大小，最大值为 Integer.MAX_VALUE ；
 *                              存在三种映射文件方式：只读，读写，专用；
 *                              a.只读：视图修改得到的缓冲区，会抛出异常（见：测试：只读模式）
 *                              b.读写: 对缓冲区的更改最终会传播到文件；该更改对映射到同一文件的其他程序不一定是可见的；
 *                              c.专用: 对得到的缓冲区的更改不会传播到文件中，并且该更改对于映射到同一文件的其他程序时不可见的；相反，会创建缓冲区已修改的部分内容的副本；
 *                              对于只读模式来说，通道必须可以读操作，但对于读写模式或者专用模式，要求通道必须可读可写；
 *                              映射的缓冲区的position为0，limit和容量为map方法的第三个参数size的个数 ，但其标记mark是不确定的；值得注意的是：在此缓冲区被垃圾回收之前，缓冲区和映射关系是有效的；
 *                                  一旦映射缓冲区和文件建立关系之后，就不再依赖创建他的文件映射的通道了，比如关闭通道对缓冲区和文件的映射关系没有任何影响；
 *
 *                          认识 MappedByteBuffer
 *                          测试：只读模式
 *                          测试：可读可写模式
 *                          测试：专用模式
 *                          测试：MappedByteBuffer的force方法
 *                          测试：MappedByteBuffer的load方法isLoaded方法
 *                              load()方法：简单解释：将缓冲区的内容加载到物理内存中，此方法最大限度的确保当返回值时，缓冲区的内容位于物理内存中，调用此方法可能会导致一些页面错误，并导致发生io操作；
 *                              isLoad()方法：简单解释：判断缓冲区的数据是否都在物理内存中，true表示所有数据极有可能都在物理内存中，因此是可以访问的，不会导致任何虚拟内存页错误，也无须任何io操作；
 *                              返回false不意味着缓冲区的内容不位于物理内存中。返回值只是一个提示，因为在该方法返回之前，某些缓冲区的数据已经被 os 移动至物理内存了( 需要理解 )；
 *                      24.打开一个文件
 *                          FileChannel.open(Path path, OpenOption... options)
 *                              path可以由File对象获取，接口 OpenOption 通常使用其实现为 StandardOpenOption ,StandardOpenOption 是一个枚举，测试用例如下：
 *                              枚举 StandardOpenOption.CREATE 和 StandardOpenOption.WRITE 一起使用，才能成功创建文件，具体代码见 test_024_1
 *                              枚举 StandardOpenOption.APPEND 的使用，具体代码见 test_024_2
 *                              枚举 StandardOpenOption.READ 的使用，具体代码见 test_024_3
 *                              枚举 StandardOpenOption.TRUNCATE_EXISTING 的使用，具体代码见 test_024_4
 *                              枚举 StandardOpenOption.CREATE_NEW 的使用，具体代码见 test_024_5
 *                              枚举 StandardOpenOption.DELETE_ON_CLOSE 的使用，具体代码见 test_024_6
 *                              枚举 StandardOpenOption.SPARSE 的使用，具体代码见 test_024_7
 *                              枚举 StandardOpenOption.SYNC 的使用，具体代码见 test_024_8
 *                              枚举 StandardOpenOption.DSYNC 的使用：要求对文件内容的每次更新都同步到设备上，SYNC和DSYNC的区别为，DSYNC 只更新内容，而SYNC更新元数据和内容，和force方法作用一样；
 *                      25.判断当前通道是否打开
 *
 *
 * @Author LP
 * @Date 2021/7/12
 * @Version 1.0
 **/
public class Channel_25_lock_FileChannel {

    /**
     *
     * 判断当前通道是否打开
     *      FileChannel.isOpen()返回true 或者 false
     * @throws IOException
     */
    @Test
    public void test_025 () throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_025")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        System.out.println(String.format("当前通道的状态：%s", fileChannel.isOpen()));

        fileChannel.close();
        //关闭通道
        System.out.println(String.format("当前通道的状态：%s", fileChannel.isOpen()));


    }


    /**
     *
     * 测试：枚举 StandardOpenOption.SYNC 的使用
     *      要求对文件内容或者元数据的每次更新都同步写入底层设备上；如果这样，运行效率就降低了，经过测试，性能降低的非常明显
     *
     * @throws IOException
     */
    @Test
    public void test_024_8 () throws IOException{
        FileChannel fileChannel = FileChannel.open(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_024_8")).toPath(),
                                StandardOpenOption.SYNC,StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            fileChannel.write(ByteBuffer.wrap("a".getBytes()));
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("执行时间：%s",end-begin));
        fileChannel.close();

    }
    /**
     * 测试：枚举 StandardOpenOption.SPARSE 的使用
     *      打开文件，创建一个文件，如果这个文件位置特别大，占用了6个G 的位置无效空间，比如下面的代码，然后后面跟一个字节a，那么不创建 稀疏 文件的话，文件占用磁盘空间 是6G，如果创建了文件类型为稀疏 ，文件占用磁盘空间 是非常小的，因为 稀疏 把那些不存储数据的空间不让其占用磁盘容量，等以后写入有效数据后再占用磁盘容量；
     *      注意：创建 系数文件的时候，要用枚举：StandardOpenOption.CREATE_NEW，不要用 StandardOpenOption.CREATE ；
     *
     * @throws IOException
     */
    @Test
    public void test_024_7 () throws IOException{
        FileChannel fileChannel = FileChannel.open(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_024_7_2")).toPath(),
                StandardOpenOption.SPARSE,StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        long fileSize = Integer.MAX_VALUE;
        fileSize = fileSize+fileSize+fileSize;
        fileChannel.position(fileSize);//设置通道的位置，从当前位置开始下面的写入操作
        int writeLen = fileChannel.write(ByteBuffer.wrap("a".getBytes()));
        fileChannel.close();

    }
    /**
     *
     * 测试：枚举 StandardOpenOption.DELETE_ON_CLOSE 的使用
     *      打开文件，创建一个文件之后，关闭通道之后标记可以删除当前文件
     *
     * @throws IOException
     */
    @Test
    public void test_024_6 () throws IOException, InterruptedException {
        FileChannel fileChannel = FileChannel.open(new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_024_6")).toPath(), StandardOpenOption.DELETE_ON_CLOSE, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        Thread.sleep(1000);
        fileChannel.close();
    }

    /**
     * 测试：枚举 StandardOpenOption.CREATE_NEW 的使用
     *      打开文件，创建一个文件，使用 StandardOpenOption.CREATE_NEW 枚举，但要和 StandardOpenOption.WRITE 一起使用，否则会报异常，同时，如果创建的这个文件存在，那么也会抛异常：文件已存在的异常；
     *
     * @throws IOException
     */
    @Test
    public void test_024_5() throws IOException{
        FileChannel fileChannel = FileChannel.open(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_024_5")).toPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        fileChannel.close();
    }

    /**
     * 测试：枚举 StandardOpenOption.TRUNCATE_EXISTING 的使用
     *      打开文件，截断访问：如果当前枚举和 写入访问枚举打开文件一起使用，执行完了程序之后，文件被截断，内容为空，如果和读取访问枚举一起使用，文件内容不改变；
     *
     * @throws IOException
     */
    @Test
    public void test_024_4() throws IOException{
        File file = new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_024_4"));
        FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.READ);
        fileChannel.close();
    }


    /**
     * 测试：枚举 StandardOpenOption.READ 的使用
     *      打开文件，进行读取操作
     * @throws IOException
     */
    @Test
    public void test_024_3() throws IOException{
        File file = new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_024_3"));
        Path path = file.toPath();
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
        //根据文件对象File对象 初始化缓存大小
        byte [] bytes = new byte[(int) file.length()];
        int readLen = fileChannel.read(ByteBuffer.wrap(bytes));
        
        //打印缓存的内容
        for (int i = 0; i < bytes.length; i++) {
            System.out.print((char)bytes[i]);
        }

    }

    /**
     * 测试：枚举 StandardOpenOption.APPEND 的使用
     *      打开文件，如果写入文件内容，以追加的方式进性操作文件
     * @throws IOException
     */
    @Test
    public void test_024_2() throws IOException{
        File file = new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_024_2"));
        Path path = file.toPath();
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.APPEND);
        int writeLen = fileChannel.write(ByteBuffer.wrap("12345".getBytes()));
        fileChannel.close();
    }


    /**
     *
     * 测试：枚举 StandardOpenOption.CREATE /StandardOpenOption.WRITE 的使用
     * 如果文件，创建文件操作
     *      FileChannel.open(Path path, OpenOption... options) ：
     *      path可以由File对象获取，接口 OpenOption 通常使用其实现为StandardOpenOption；
     *      对于 StandardOpenOption.CREATE /StandardOpenOption.WRITE的使用，StandardOpenOption.CREATE 不能单独使用，否则会报错，因为这个枚举只是一个意图，要和 StandardOpenOption.WRITE 一起使用，才能成功创建文件；
     *
     * @throws IOException
     */
    @Test
    public void test_024_1() throws IOException{
        File file = new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_024x"));
        Path path = file.toPath();
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE,StandardOpenOption.WRITE);
        //FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        fileChannel.close();//关闭通道

    }
    /**
     * 测试：MappedByteBuffer的load 方法 / isLoaded 方法
     *
     * 此程序需要linux测试：在windows测试isLoad方法永远返回false
     *
     *
     * @throws IOException
     */
    @Test
    public void test_023_5() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_023_5")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 100);
        System.out.println(String.format("映射缓冲区是否加载到物理内存中:%s" , mappedByteBuffer.isLoaded()));
        mappedByteBuffer.load();
        System.out.println(String.format("映射缓冲区是否加载到物理内存中:%s" , mappedByteBuffer.isLoaded()));
        fileChannel.close();
        randomAccessFile.close();
    }

    /**
     * 测试：MappedByteBuffer的force方法
     *      强制将缓冲区所做的内容更改写入映射文件所在的设备上（如果文件在设备上，可以保证映射缓冲区创建以来或者最后一次调用此方法以来，正常写入设备，如果映射文件不在本地设备，无法保证）；
     *      注意：此映射缓冲区必须是在 通道映射的模式为 读写模式映射的才行，否则调用此force方法无效！！
     *      当然，调用此方法，在运行效率上会下降
     * @throws IOException
     */
    @Test
    public void test_023_4() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannetest_023_4")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 100);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            mappedByteBuffer.put("a".getBytes());
            //mappedByteBuffer.force();//把当前映射缓冲区的内容强制写到文件中，会有性能的问题
        }
        long end = System.currentTimeMillis();
        System.out.println(end-begin);
        fileChannel.close();
        randomAccessFile.close();
    }

    /**
     * 测试：专用模式
     * 对于专用模式，不更改底层文件内容
     * @throws IOException
     */
    @Test
    public void test_023_3() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_023_3")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.PRIVATE, 3, 4);
        System.out.println(String.format("mappedByteBuffer.position=%s,mappedByteBuffer.limit=%s,mappedByteBuffer.capacity=%s", mappedByteBuffer.position(),mappedByteBuffer.limit(),mappedByteBuffer.capacity()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        mappedByteBuffer.position(0);
        mappedByteBuffer.put((byte)'o' );
        mappedByteBuffer.put((byte)'1' );
        mappedByteBuffer.put((byte)'2' );
        mappedByteBuffer.put((byte)'3' );
        fileChannel.close();
        randomAccessFile.close();

    }

    /**
     *
     * 测试：可读可写模式
     * 文件初识内容为abcde，被改为o123e
     *
     * @throws IOException
     */
    @Test
    public void test_023_2() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_023_2")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        mappedByteBuffer.position(0);
        mappedByteBuffer.put((byte)'o' );
        mappedByteBuffer.put((byte)'1' );
        mappedByteBuffer.put((byte)'2' );
        mappedByteBuffer.put((byte)'3' );
        fileChannel.close();
        randomAccessFile.close();





    }

    /**
     * 测试：只读模式
     * @throws IOException
     */
    @Test
    public void test_023_1() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_023_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 5);//只读模式
//        mappedByteBuffer.putChar('1');//因为是只读的，不能更改数据
        mappedByteBuffer.put("a".getBytes());//因为是只读的，不能更改数据

    }
    /**
     *
     * 认识 MappedByteBuffer:map(MapMode mode,long position, long size)
     *         MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 5);
     *         map.force();//强制把缓冲区内容写包含映射文件的设备上
     *         map.isLoaded();//判断是否在物理内存中
     *         map.load();//buffer->cache
     *
     * @throws IOException
     */
    @Test
    public void test_023() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_023")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 5);//
        //        mappedByteBuffer.force();// 强制把缓冲区内容写到设备上
        //        mappedByteBuffer.isLoaded();// 判断是否在物理内存中
        //        mappedByteBuffer.load();// buffer->cache
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));
        mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,2,2);
        //缓冲区第0个位置是c
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));//c
        System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position()));//d
        //System.out.println(String.format("%s position=%s", (char)mappedByteBuffer.get(),mappedByteBuffer.position())); //
        Thread.sleep(500);
        fileChannel.close();
        randomAccessFile.close();

        //byte[] array = map.array();
        //        map.limit();
        //        map.position();
        //        map.capacity();

    }

    /**
     *
     * 测试 FileChannel.force(boolean metaData); 的性能
     *
     * @throws IOException
     */
    @Test
    public void test_022() throws IOException {
        File file = new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_022"));
        if (file.exists()){//如果文件存在
            file.delete();
        } else {
            file.createNewFile();//如果不存在,创建文件
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel fileChannel = fileOutputStream.getChannel();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 5000 ; i++) {
            fileChannel.write(ByteBuffer.wrap("abcde".getBytes()));
            fileChannel.force(false);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-begin);
        fileChannel.close();
        fileOutputStream.close();
    }



    /**
     *
     * FileLock.overlaps()作用：判断此锁定是否与给定锁定区域重叠;
     *
     * @throws IOException
     */
    @Test
    public void test_021_2() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_021")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock lock = fileChannel.lock(1, 10, true);
        //boolean overlaps = lock.overlaps(2, 5);//返回true，至少重叠1个字节成立
        boolean overlaps = lock.overlaps(11, 12);
        System.out.println(String.format("overlaps=%s", overlaps));
    }

    /**
     *
     * 该示例说明几点：
     * 1.通过锁对象FileLock获取通道表示当前上锁文件的通道对象，最新jdk中，锁对象的方法：acquiredBy() 也是返回锁定文件通道对象的；
     * 2.FileLock.release() 可以让当前的锁无效，FileLock.close() 方法底层就是调用的 release() 方法；
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test_021_1() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile(  "Channel_25_lock_FileChannel_test_021")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        System.out.println(String.format("filechannel.hashCode=%s", fileChannel.hashCode()));//
        FileLock lock = fileChannel.lock(1,10,true);
        System.out.println(String.format("lock.position=%s,lock.size=%s,lock.isValid=%s,lock.isShared=%s,lock.channel.hashcode=%s,lock.acquiredby.hashcode=%s",
                lock.position(),lock.size(),lock.isValid(),lock.isShared(),lock.channel().hashCode(),lock.acquiredBy().hashCode()));
        lock.release();//释放锁对象
        lock = fileChannel.lock(1, 10, false);//独占锁
        System.out.println(String.format("lock.position=%s,lock.size=%s,lock.isValid=%s,lock.isShared=%s,lock.channel.hashcode=%s,lock.acquiredby.hashcode=%s",
                lock.position(),lock.size(),lock.isValid(),lock.isShared(),lock.channel().hashCode(),lock.acquiredBy().hashCode()));
        lock.close();
        fileChannel.close();
        System.out.println(String.format("lock.position=%s,lock.size=%s,lock.isValid=%s,lock.isShared=%s,lock.channel.hashcode=%s,lock.acquiredby.hashcode=%s",
                lock.position(),lock.size(),lock.isValid(),lock.isShared(),lock.channel().hashCode(),lock.acquiredBy().hashCode()));




    }


    /**
     * 测试当前获取锁的方法tryLock为非阻塞的
     *      先执行test_019_1，再执行test_019_2，test_019_2 返回的锁为空 ，
     *      因为 test_019_1 获取的是独占锁，如果是共享锁，
     *      则 test_019_2 就可以获取锁了；
     *
     * @throws IOException
     */
    @Test
    public void test_019_2() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile(Channel_25_lock_FileChannel.class.getName() + "_test_019_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock fileLock = fileChannel.tryLock(0, 3, true);
        System.out.println(String.format("%s", fileLock));
        Thread.sleep(Integer.MAX_VALUE);
        randomAccessFile.close();
        fileChannel.close();
    }
    /**
     * 测试当前获取锁的方法tryLock为非阻塞的
     * @throws IOException
     */
    @Test
    public void test_019_1() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile(Channel_25_lock_FileChannel.class.getName() + "_test_019_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock fileLock = fileChannel.tryLock(0, 5, false);
        System.out.println(String.format("%s", fileLock));
        Thread.sleep(Integer.MAX_VALUE);
        randomAccessFile.close();
        fileChannel.close();
    }

    /**
     * 各字节类型表示文件内容的最大值（单位为字节）
     */
    @Test
    public void test01() {
        System.out.println("Byte: "+Byte.MAX_VALUE+"###"+Byte.MIN_VALUE+" byte_number: "+Byte.SIZE);
        System.out.println("Short: "+Short.MAX_VALUE+"###"+Short.MIN_VALUE+" byte_number: "+Short.SIZE);
        System.out.println("Integer: "+Integer.MAX_VALUE+"###"+Integer.MIN_VALUE+" byte_number: "+Integer.SIZE);
        System.out.println("Long: "+Long.MAX_VALUE+"###"+Long.MIN_VALUE+" byte_number: "+Long.SIZE);
        System.out.println("Float: "+Float.MAX_VALUE+"###"+Float.MIN_VALUE+" byte_number: "+Float.SIZE);
        System.out.println("Double: "+Double.MAX_VALUE+"###"+Double.MIN_VALUE+" byte_number: "+Double.SIZE);
        System.out.println("Char: "+" byte_number: "+Character.SIZE);
    }

    /**
     *
     * FileLock lock()方法的探究：锁定文件大小为Long的最大值，且为互斥锁，上锁的方法就是lock方法；
     *
     * @throws IOException
     */
    @Test
    public void test_015() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_015")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock lock = fileChannel.lock();

        /**
         * lock():
         * 源码：
         *  public final FileLock lock() throws IOException {
         *         return lock(0L, Long.MAX_VALUE, false);
         *  }
         *
         */


    }


    /**
     * 测试：共享锁之间不是互斥关系
     * test_014_1加了共享锁，test_014_2再次获取一次共享锁，也是成功的，这就验证了测试的结论
     * @throws IOException
     */
    @Test
    public void test_014_2 () throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_014_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        //fileChannel.lock(0,Integer.MAX_VALUE,true);
        fileChannel.lock(0,Integer.MAX_VALUE,false);//测试第15种情况
        System.out.println("B 获取共享锁成功");

    }

    /**
     * 测试：共享锁之间不是互斥关系
     * @throws IOException
     */
    @Test
    public void test_014_1 () throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChanneltest_014_1")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        //对该文件提前加上共享锁
        fileChannel.lock(0,Integer.MAX_VALUE,true);
        Thread.sleep(Integer.MAX_VALUE);

    }











    /**
     *
     * 提前锁定
     *
     * 就是当文件大小小于position时，提前在position 位置加锁
     *
     * @throws IOException
     */
    @Test
    public void test_013() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_013")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        //初始化2个字节的文本大小
        fileChannel.write(ByteBuffer.wrap("ab".getBytes()));
        //提起锁定位置position=6，范围为2的字节序列
        fileChannel.lock(6,2,true);//加上共享锁

        //此种情况下，可以再写 4 个字节
        for (int i = 1; i <= 10; i++) {
            if (i==5){ //跳过加锁的范围，如果有这个代码就不会在抛异常了
                fileChannel.position(8);
            }
            System.out.println(i);
            fileChannel.write(ByteBuffer.wrap((i+"").getBytes()));
        }
    }



    /**
     *
     * 验证lock方法参数position和size的含义
     *
     * 上面是对锁的特性的研究，当前单元测试是对lock参数position，size的了解，其实position表示从哪个地方上锁，size表示上锁的区域范围；
     * @throws IOException
     */
    @Test
    public void test_012() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FilePahtVar.getFile("Channel_25_lock_FileChannel_test_012")), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        //init content
        fileChannel.write(ByteBuffer.wrap("abc123".getBytes()));


        System.out.println(String.format("通道的位置：%s",fileChannel.position()));//6
        fileChannel.position(0);
        System.out.println(String.format("通道的位置：%s",fileChannel.position()));//0
        fileChannel.lock(3,2,true);//加上共享锁

        fileChannel.write(ByteBuffer.wrap("1".getBytes()));//index=0
        System.out.println(String.format("1 通道的位置：%s",fileChannel.position()));
        fileChannel.write(ByteBuffer.wrap("2".getBytes()));//index=1
        System.out.println(String.format("2 通道的位置：%s",fileChannel.position()));
        fileChannel.write(ByteBuffer.wrap("3".getBytes()));//index=2
        System.out.println(String.format("3 通道的位置：%s",fileChannel.position()));//此时position为3

//        fileChannel.write(ByteBuffer.wrap("4".getBytes()));//index=3：此时是向position为3 的位置进行写入4这个字节的操作
//        System.out.println(String.format("4 通道的位置：%s",fileChannel.position()));//抛异常

        fileChannel.position(5);
        fileChannel.write(ByteBuffer.wrap("56".getBytes()));//index=5：跳过position=3，范围为2，也就是[3,4]，所以测试对position=5位置进行写操作，不抛异常
        System.out.println(String.format("56 通道的位置：%s",fileChannel.position()));//此时 position 为 7






    }





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


