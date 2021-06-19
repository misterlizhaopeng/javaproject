package nio_test.nio_socket.buffer;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.buffer.Buffer_ByteBuffer_equals
 * @Deacription : equals:两个容器的 capacity 可以不同，说明equals比较的是 position 和 limit 之间的内容是否完全一样；
 * @Author LP
 * @Date 2021/6/19
 * @Version 1.0
 **/
public class Buffer_ByteBuffer_equals {
    @Test
    public void test_01(){
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        boolean isEqual = byteBuffer.equals(byteBuffer);
        System.out.println(isEqual);
    }
}

