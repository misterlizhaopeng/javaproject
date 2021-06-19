package nio_test.nio_socket.buffer;

import org.junit.Test;

import java.nio.*;

/**
 * @ClassName nio_test.nio_socket.Buffer_ByteBuffer_asX_Type
 * @Deacription 视图缓冲区的三个优势：
                             * 1.视图缓冲区不是根据字节进行索引，而是根据特定类型的值的大小进行索引；
                             * 2.视图缓冲区提供了相对批量put和get方法，这些方法可在缓冲区和数组或相同类型的其他缓冲区之间传输值的连续序列；
                             * 3.视图缓冲区可能更高效，这是因为当且仅当其支持的字节缓冲区为直接缓冲区时，它才是直接缓冲区；
                             * 上面三点分别验证：test02；test03；test04；
 * @Author LP
 * @Date 2021/6/9 16:07
 * @Version 1.0
 **/
public class Buffer_ByteBuffer_asX_Type {
    /**
     * 验证：3.视图缓冲区可能更高效，这是因为当且仅当其支持的字节缓冲区为直接缓冲区时，它才是直接缓冲区；
     */
    @Test
    public void test04() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        byteBuffer.putInt(123);
        byteBuffer.putInt(456);
        byteBuffer.flip();
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());

        System.out.println();

        System.out.println("byteBuffer 为直接缓冲区，效率比较快...");
        System.out.println(byteBuffer);
        System.out.println("由于 byteBuffer 是直接缓冲区，所以 intBuffer 也是直接缓冲区了：");
        System.out.println(intBuffer);




    }

    /**
     * 验证：1.视图缓冲区提供了相对批量put和get方法，这些方法可在缓冲区和数组或相同类型的其他缓冲区之间传输值的连续序列；
     */
    @Test
    public void test03() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.putInt(123);
        byteBuffer.putInt(456);
        byteBuffer.flip();

        System.out.println(String.format("byteBuffer.position=%s,value=%s", byteBuffer.position(), byteBuffer.getInt()));
        System.out.println(String.format("byteBuffer.position=%s,value=%s", byteBuffer.position(), byteBuffer.getInt()));
        System.out.println(String.format("byteBuffer.position=%s", byteBuffer.position()));

        System.out.println();

        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(456);
        intBuffer.put(789);
        intBuffer.flip();
        System.out.println(String.format("intBuffer.position=%s,value=%s", intBuffer.position(), intBuffer.get()));
        System.out.println(String.format("intBuffer.position=%s,value=%s", intBuffer.position(), intBuffer.get()));
        System.out.println(String.format("intBuffer.position=%s", intBuffer.position()));




    }

    /**
     * 验证：1.视图缓冲区不是根据字节进行索引，而是根据特定类型的值的大小进行索引；
     * 结论：从运行结果来看：ByteBuffer 是按照字节进行存储， IntBuffer 是按 数据类型为单位进行存储；
     */
    @Test
    public void test02() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println("A1=" + byteBuffer.position());
        byteBuffer.putInt(123);
        System.out.println("A2=" + byteBuffer.position());
        byteBuffer.putInt(456);
        System.out.println("A3=" + byteBuffer.position());

        System.out.println();

        IntBuffer intBuffer = IntBuffer.allocate(10);
        System.out.println("B1=" + intBuffer.position());
        intBuffer.put(345);
        System.out.println("B2=" + intBuffer.position());
        intBuffer.put(789);
        System.out.println("B3=" + intBuffer.position());


    }

    @Test
    public void test01() {
        //byte add double,and as double buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.putDouble(1.1D);
        byteBuffer.putDouble(1.2D);
        byteBuffer.putDouble(1.3D);
        byteBuffer.putDouble(1.4D);
        byteBuffer.flip();
        DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
        for (int i = 0; i < doubleBuffer.capacity(); i++) {
            System.out.print(doubleBuffer.get(i) + " ");
        }
        System.out.println();

        //byte add float,and as float buffer
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(16);
        byteBuffer2.putFloat(2.1F);
        byteBuffer2.putFloat(2.2F);
        byteBuffer2.putFloat(2.3F);
        byteBuffer2.putFloat(2.4F);
        byteBuffer2.flip();
        FloatBuffer floatBuffer = byteBuffer2.asFloatBuffer();
        for (int i = 0; i < floatBuffer.capacity(); i++) {
            System.out.print(floatBuffer.get(i) + " ");
        }

        ByteBuffer byteBuffer3 = ByteBuffer.allocate(16);
        byteBuffer3.putInt(31);
        byteBuffer3.putInt(32);
        byteBuffer3.putInt(33);
        byteBuffer3.putInt(34);
        System.out.println();
        System.out.println(String.format("byteBuffer3.position=%s,byteBuffer3.capacity=%s,byteBuffer3.limit=%s", byteBuffer3.position(), byteBuffer3.capacity(), byteBuffer3.limit()));
        byteBuffer3.flip();
        IntBuffer intBuffer = byteBuffer3.asIntBuffer();
        System.out.println(String.format("byteBuffer3.position=%s,byteBuffer3.capacity=%s,byteBuffer3.limit=%s", byteBuffer3.position(), byteBuffer3.capacity(), byteBuffer3.limit()));
        //byte add int,and as int buffer
        for (int i = 0; i < intBuffer.capacity(); i++) {
            System.out.print(intBuffer.get(i) + " ");
        }

        System.out.println();

        //byte add long,and as long buffer
        ByteBuffer byteBuffer4 = ByteBuffer.allocate(32);
        byteBuffer4.putLong(41L);
        byteBuffer4.putLong(42L);
        byteBuffer4.putLong(43L);
        byteBuffer4.putLong(44L);
        byteBuffer4.flip();
        LongBuffer longBuffer = byteBuffer4.asLongBuffer();
        for (int i = 0; i < longBuffer.capacity(); i++) {
            System.out.print(longBuffer.get(i) + " ");
        }
        System.out.println();

        //byte add short,and as short buffer
        ByteBuffer byteBuffer5 = ByteBuffer.allocate(8);
        byteBuffer5.putShort((short) 51);
        byteBuffer5.putShort((short) 52);
        byteBuffer5.putShort((short) 53);
        byteBuffer5.putShort((short) 54);
        byteBuffer5.flip();
        ShortBuffer shortBuffer = byteBuffer5.asShortBuffer();
        for (int i = 0; i < shortBuffer.capacity(); i++) {
            System.out.print(shortBuffer.get(i) + " ");
        }


    }
}

