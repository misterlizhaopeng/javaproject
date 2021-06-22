package nio_test.nio_socket.channel;

import org.junit.Test;

import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_2_Test
 * @Deacription : 该接口的作用：使通道支持异步；
 *
 *                  当一个通道实现了可异步/可关闭的接口时，若在I/O的时候，调用这个通道的close()方法，就会发送I/O异常，并且出现 AsynchronousCloseException;
 *
 *                  异步通道在多线程情况下是安全的。
 *                  某些异步通道在多线程的情况下，某些通道的实现是支持并发读和写的，但是在不允许在一个未完成的I/O操作上再次进行read或者write操作；
 *                  异步通道支持取消操作，Future 接口定义的cancel()方法来取消执行。这会导致正在等待处理I/O结果的线程抛出 CancellationException异常；
 *
 *                  ...
 *
 *                  在调用取消cancel()方法时，对参数mayInterruptIfRunning传true，io 操作有可能会中断。在这个时候，所有等待io 的线程将会抛出异常（CancellationException 异常），并且在此通道中的其他操作也会抛出异常（AsynchronousCloseException异常）；
 *                  在调用取消方法时，建议废除缓冲区的数据，因为此时缓冲区的数据有可能不完整，如果再次打开通道，尽量避免访问这些缓冲区
 *
 *
 *
 * @Author LP
 * @Date 2021/6/22
 * @Version 1.0
 **/
public class Channel_2_AsynchronousChannel {

    @Test
    public void test_01() {
        //接口的继承关系：AsynchronousChannel->Channel->Closeable->AutoCloseable
    }
}

