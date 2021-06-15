package nio_test.nio_socket;

import org.junit.Test;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @ClassName nio_test.nio_socket.Buffer_Order
 * @Deacription :order 的获取和设置字节顺序
 *
 * @Author LP
 * @Date 2021/6/15
 * @Version 1.0
 **/
public class Buffer_Order {

    /**
     * 如果字节顺序不一致，那么在获取数据时就会出现错误的值；
     */
    @Test
    public void test_02() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.putInt(123);
        byteBuffer.putInt(567);
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getInt());

        ByteBuffer byteBuffer2 = ByteBuffer.wrap(byteBuffer.array());
        byteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(byteBuffer2.getInt());
        System.out.println(byteBuffer2.getInt());


    }

    /**
     *   有的cpu从高位开始读，有的cpu从低位开始读字节内容，当这两种cpu传递数据时，就要将字节的排列顺序进行统一，此时order（ByteOrder bo）方法就有用了，他的作用就是设置字节的排列顺序；
     *  order() 表示：获取缓冲区字节顺序；order(ByteOrder bo) 表示：修改此缓冲区的字节顺序，默认情况下，字节默认顺序为：ByteOrder.BIG_ENDIAN
     *  ByteOrder.BIG_ENDIAN：表示 BIG_ENDIAN 字节顺序的常量，多字节顺序从最高有效位到最低有效位；
     *  ByteOrder.LITTLE_ENDIAN：表示 LITTLE_ENDIAN 字节顺序的常量，多字节顺序从最低有效位到最高有效位；
     */
    @Test
    public void test_01() {

        int value = 123456789;
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        System.out.print(String.format("%s ", byteBuffer.order()));//big_endian
        System.out.print(String.format("%s ", byteBuffer.order()));//big_endian
        byteBuffer.putInt(value);
        byte[] array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();

        byteBuffer = ByteBuffer.allocate(4);
        System.out.print(String.format("%s ", byteBuffer.order()));
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        System.out.print(String.format("%s ", byteBuffer.order()));
        byteBuffer.putInt(value);
        array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();


        byteBuffer = ByteBuffer.allocate(4);
        System.out.print(String.format("%s ", byteBuffer.order()));
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        System.out.print(String.format("%s ", byteBuffer.order()));
        byteBuffer.putInt(value);
        array = byteBuffer.array();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }


    }
}

