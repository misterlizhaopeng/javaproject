package nio_test.nio_socket.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.buffer.Buffer_compact
 * @Deacription : 总结：什么是compact，就是把position到limit的区间往前移动到数组头部(覆盖数组position位置之前的项)，
 *                     此时position为数组移动后的后面的position，limit 为数组容量的值；
 * @Author LP
 * @Date 2021/6/19
 * @Version 1.0
 **/
public class Buffer_ByteBuffer_compact {
    @Test
    public void test_01() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.println(String.format("A capacity=%s , position=%s , limit=%s ", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit()));
        System.out.println(String.format("1 getValue=%s",byteBuffer.get()));
        System.out.println(String.format("B capacity=%s , position=%s , limit=%s ", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit()));

        System.out.println(String.format("2 getValue=%s",byteBuffer.get()));
        System.out.println(String.format("C capacity=%s , position=%s , limit=%s ", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit()));

        //System.out.println(String.format("3 getValue=%s",byteBuffer.get()));

        byteBuffer.compact();//压缩下
        System.out.println("byteBuffer.compact()");
        System.out.println(String.format("D capacity=%s , position=%s , limit=%s ", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit()));



        byteBuffer.flip();//flip 把limit设置为position，把position设置为0，mark设置为-1；flip之后，读取压缩后的内容；
        System.out.println("byteBuffer.flip()");
        System.out.println(String.format("E capacity=%s , position=%s , limit=%s ", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit()));




    }
}