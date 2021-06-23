package nio_test.nio_socket.channel;

import org.junit.Test;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_12_InterruptibleChannel
 * @Deacription : InterruptibleChannel 使通道能以异步的方式进行关闭和中断 ;
 *                  当通道实现了 asynchronously、closeable 特性，如果 一个线程 在可以被中断的通道上出现了阻塞，那么当其他线程调用这个通道的close方法时，这个阻塞的 线程会收到 AsynchronousCloseException 异常；
 *                  当通道实现了 asynchronously、closeable 特性的同时还实现了 interrupt特性，如果 一个线程 在可以被中断的通道上出现了阻塞，那么当其他线程调用该阻塞线程的interrupt方法后，通道将关闭，阻塞线程将会收到 ClosedByInterruptException 异常；
 *
 *
 *
 *
 * @Author LP
 * @Date 2021/6/23
 * @Version 1.0
 **/
public class Channel_12_InterruptibleChannel {
    @Test
    public void test_01(){
        //接口的继承关系：AsynchronousByteChannel->AsynchronousChannel->Channel->Closeable->AutoCloseable
        //                ScatteringByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                 GatheringByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //                          ByteChannel->ReadableByteChannel->Channel->Closeable->AutoCloseable
        //                          ByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //     SeekableByteChannel->ByteChannel->WritableByteChannel->Channel->Closeable->AutoCloseable
        //                          MulticastChannel->NetworkChannel->Channel->Closeable->AutoCloseable
        //                                      InterruptibleChannel->Channel->Closeable->AutoCloseable

    }
}

