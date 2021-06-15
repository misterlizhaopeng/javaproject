package nio_test.nio_socket;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_OnlyRead
 * @Deacription :
 * @Author LP
 * @Date 2021/6/15
 * @Version 1.0
 **/
public class Buffer_OnlyRead {

    /**
     * 创建共享此缓存区内容的新的只读缓冲区；新的缓冲区的内容为此缓冲区的内容，此缓冲区内容的更改在只读缓冲区可见，但新的缓冲区内容只能读取；
     * 位置、限制和标志相互独立，对于新的缓冲区的这三个值和此缓冲区相同；
     */
    @Test
    public void test_01() {
        byte[] byteArrayIn = {1, 2, 3, 4, 5};
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayIn);
        ByteBuffer asReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(String.format("byteBuffer.isReadOnly()=%s",byteBuffer.isReadOnly()));
        System.out.println(String.format("asReadOnlyBuffer.isReadOnly()=%s",asReadOnlyBuffer.isReadOnly()));
        asReadOnlyBuffer.rewind();

        asReadOnlyBuffer.putInt((byte)123);




    }
}

