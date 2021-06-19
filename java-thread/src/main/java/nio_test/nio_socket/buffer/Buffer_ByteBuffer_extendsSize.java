package nio_test.nio_socket.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.buffer.Buffer_ByteBuffer_extendsSize
 * @Deacription : 扩容：一旦缓冲区创建成功，capacity就确定了，不能改变了；如果对缓冲区进行扩容，就的进行相应的处理；
 *                  方法：ByteBuffer.allocate() 结合缓冲区对象的方法 put() 进行扩容；
 *
 * @Author LP
 * @Date 2021/6/19
 * @Version 1.0
 **/
public class Buffer_ByteBuffer_extendsSize {

    private static ByteBuffer extendsSize(ByteBuffer buffer, int extendsSize) {
        ByteBuffer newByteBuffer = ByteBuffer.allocate(buffer.capacity() + extendsSize);
        newByteBuffer.put(buffer);
        return newByteBuffer;
    }

    @Test
    public void test_01() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        ByteBuffer newByteBuffer = extendsSize(byteBuffer, 20);

        System.out.println(String.format("newByteBuffer capacity=%s , position=%s , limit=%s ", newByteBuffer.capacity(), newByteBuffer.position(), newByteBuffer.limit()));

        newByteBuffer.position(0);
        for (int i = newByteBuffer.position(); i < newByteBuffer.limit(); i++) {
            System.out.print(String.format("%s ", newByteBuffer.get()));
        }

        System.out.println();
        System.out.println("array below");
        byte[] array = newByteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print(String.format("%s ", array[i]));
        }

        /**
         * 下面为输出结果：
         *
         * newByteBuffer capacity=25 , position=5 , limit=25
         * 1 2 3 4 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
         * array below
         * 1 2 3 4 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
         */

    }
}

