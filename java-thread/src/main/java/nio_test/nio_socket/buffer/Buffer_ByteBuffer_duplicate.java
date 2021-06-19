package nio_test.nio_socket.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.buffer.Buffer_ByteBuffer_duplicate
 * @Deacription : duplicate的功能：创建共享此缓冲区内容的新的字节缓冲区。新的缓冲区的内容变动影响旧的缓冲区的内容，反之亦然。
 *                      创建新的缓冲区的位置，限定以及容量和此缓冲区是一样的，但是这三个值在这俩缓冲区是独立的；
 *                      当且仅当此缓冲区为直接缓冲区，新的缓冲区才为直接缓冲区；
 *                      当且仅当此缓冲区为只读缓冲区，新的缓冲区才为只读缓冲区；
 * <p>
 * <p>
 * <p>
 *                      duplicate 和 slice 的区别：
 *                      使用duplicate 、slice都是创建新的缓冲区对象，但都还是使用原来缓冲区中的 byte[] 字节数组；
 *
 *
 * @Author LP
 * @Date 2021/6/19
 * @Version 1.0
 **/
public class Buffer_ByteBuffer_duplicate {
    @Test
    public void test_01() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.position(2);
        System.out.println(String.format("byteBuffer capacity=%s , position=%s , limit=%s ", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit()));

        ByteBuffer byteBuffer2 = byteBuffer.slice();
        ByteBuffer byteBuffer3 = byteBuffer.duplicate();

        //byteBuffer4 和 byteBuffer 指向的是同一个地址
        ByteBuffer byteBuffer4 = byteBuffer;

        System.out.println(String.format("byteBuffer2 capacity=%s , position=%s , limit=%s ", byteBuffer2.capacity(), byteBuffer2.position(), byteBuffer2.limit()));
        System.out.println(String.format("byteBuffer3 capacity=%s , position=%s , limit=%s ", byteBuffer3.capacity(), byteBuffer3.position(), byteBuffer3.limit()));


        //byteBuffer2.position(0);
        //输出 3 4 5
        for (int i = byteBuffer2.position(); i < byteBuffer2.limit(); i++) {
            System.out.print(String.format("%s ", byteBuffer2.get(i)));
            
        }

        System.out.println();

        byteBuffer3.position(0);
        //输出 1 2 3 4 5
        for (int i = byteBuffer3.position(); i < byteBuffer3.limit(); i++) {
            System.out.print(String.format("%s ", byteBuffer3.get(i)));
        }


    }
}

