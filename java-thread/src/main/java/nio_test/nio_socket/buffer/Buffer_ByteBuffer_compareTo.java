package nio_test.nio_socket.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.buffer.Buffer_ByteBuffer_compareTo
 * @Deacription : 小结：通过equals 和compareTo的源码比较，就是比较两个缓冲区的position和limit之间字节是否逐个相同；
 * @Author LP
 * @Date 2021/6/19
 * @Version 1.0
 **/
public class Buffer_ByteBuffer_compareTo {

    /**
     * 如果在开始和结束之间的每个字节都一样，则返回remaining之间的减数；
     */
    @Test
    public void test_02() {
        byte[] bytes1 = new byte[]{3, 4, 5};
        byte[] bytes2 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


        ByteBuffer byteBuffer1 = ByteBuffer.wrap(bytes1);
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(bytes2);
        System.out.println(byteBuffer2.limit());
        byteBuffer1.position(0);
        byteBuffer2.position(2);//
        System.out.println(byteBuffer1.compareTo(byteBuffer2));//3-8=-5,3:为第一缓冲区的limit-position，8为第二个缓冲区的limit-position

    }
    /**
     * 如果在开始和结束之间，有一个不同的字节，则返回两者的减数
     */
    @Test
    public void test_01() {
        byte[] bytes1 = new byte[]{3, 4, 5};
        byte[] bytes2 = new byte[]{1, 2, 3, 104, 5, 6, 7, 8, 9, 10};

        ByteBuffer byteBuffer1 = ByteBuffer.wrap(bytes1);
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(bytes2);
        byteBuffer1.position(0);
        byteBuffer2.position(2);//
        System.out.println(byteBuffer1.compareTo(byteBuffer2));

    }
}

