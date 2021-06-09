package nio_test.nio_socket;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * @ClassName nio_test.nio_socket.Buffer_ByteBuffer_asCharBuffer
 * @Deacription TODO
 * @Author LP
 * @Date 2021/6/9 14:30
 * @Version 1.0
 **/
public class Buffer_ByteBuffer_asCharBuffer {

    /**
     * 使用asCharBuffer方法获得 CharBuffer 后，对 ByteBuffer 的更改会直接影响 CharBuffer中的值；
     * @throws UnsupportedEncodingException
     */
    @Test
    public void test04() throws UnsupportedEncodingException {
        byte[] bytes = "我是中国人".getBytes("utf-16be");
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        System.out.println(String.format("charBuffer.position=%s,charBuffer.capacity=%s,charBuffer.limit=%s", charBuffer.position(), charBuffer.capacity(), charBuffer.limit()));
        byteBuffer.put(2,"为".getBytes("utf-16be")[0]);
        byteBuffer.put(3,"为".getBytes("utf-16be")[1]);
        for (int i = 0; i < charBuffer.capacity(); i++) {
            System.out.print(charBuffer.get(i));
        }
    }



    /**
     * charBuffer 的get方法是utf-16be编码方式-乱码测试：-解决乱码的方式
     * 解决思路-2：在把byteBuffer生成charBuffer 的时候，转换为utf-8 的 charbuffer，首先字节缓冲区也要设置为utf-8
     */
    @Test
    public void test03() throws UnsupportedEncodingException {
        byte[] bytes = "我是中国人".getBytes("utf-8");
        //获取当前运行环境的编码方式
        System.out.println(Charset.defaultCharset().name());//UTF-8
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        //byteBuffer =java.nio.HeapByteBuffer
        System.out.println(String.format("byteBuffer =%s", byteBuffer.getClass().getName()));


        //CharBuffer charBuffer = byteBuffer.asCharBuffer();
        CharBuffer charBuffer = Charset.forName("utf-8").decode(byteBuffer);

        //charBuffer =java.nio.ByteBufferAsCharBufferB
        System.out.println(String.format("charBuffer =%s", charBuffer.getClass().getName()));
        System.out.println(String.format("byteBuffer.position=%s,byteBuffer.capacity=%s,byteBuffer.limit=%s", byteBuffer.position(), byteBuffer.capacity(), byteBuffer.limit()));
        System.out.println(String.format("charBuffer.position=%s,charBuffer.capacity=%s,charBuffer.limit=%s", charBuffer.position(), charBuffer.capacity(), charBuffer.limit()));

        charBuffer.position(0);
        //
        for (int i = 0; i < charBuffer.limit(); i++) {
            //get方法使用的是utf-16be编码方式，utf-8和utf-16be并不是一种编码方式，此处就会出现了乱码
            System.out.print(charBuffer.get(i));
        }

    }


    /**
     * charBuffer 的get方法是utf-16be编码方式-乱码测试：-解决乱码的方式
     * 解决思路-1：在生成字节数组的时候，直接将编码方式定义为 ByteBufferAsCharBufferB 的 get 获取数据的编码方式
     */
    @Test
    public void test02() throws UnsupportedEncodingException {
        byte[] bytes = "我是中国人".getBytes("utf-16be");
        //获取当前运行环境的编码方式
        System.out.println(Charset.defaultCharset().name());//UTF-8
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        //byteBuffer =java.nio.HeapByteBuffer
        System.out.println(String.format("byteBuffer =%s", byteBuffer.getClass().getName()));

        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        //charBuffer =java.nio.ByteBufferAsCharBufferB
        System.out.println(String.format("charBuffer =%s", charBuffer.getClass().getName()));
        System.out.println(String.format("byteBuffer.position=%s,byteBuffer.capacity=%s,byteBuffer.limit=%s", byteBuffer.position(), byteBuffer.capacity(), byteBuffer.limit()));
        System.out.println(String.format("charBuffer.position=%s,charBuffer.capacity=%s,charBuffer.limit=%s", charBuffer.position(), charBuffer.capacity(), charBuffer.limit()));
        charBuffer.position(0);
        for (int i = 0; i < charBuffer.capacity(); i++) {
            //get方法使用的是utf-16be编码方式，utf-8和utf-16be并不是一种编码方式，此处就会出现了乱码
            System.out.print(charBuffer.get(i));
        }

    }

    /**
     * charBuffer 的get方法是utf-16be编码方式- 乱码测试：
     */
    @Test
    public void test01() {
        byte[] bytes = "我是中国人".getBytes();
        //获取当前运行环境的编码方式
        System.out.println(Charset.defaultCharset().name());//UTF-8
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.println(String.format("byteBuffer =%s", byteBuffer.getClass().getName()));//byteBuffer =java.nio.HeapByteBuffer

        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        System.out.println(String.format("charBuffer =%s", charBuffer.getClass().getName()));//charBuffer =java.nio.ByteBufferAsCharBufferB
        //byteBuffer.position=0,byteBuffer.capacity=15,byteBuffer.limit=15
        System.out.println(String.format("byteBuffer.position=%s,byteBuffer.capacity=%s,byteBuffer.limit=%s", byteBuffer.position(), byteBuffer.capacity(), byteBuffer.limit()));
        //charBuffer.position=0,charBuffer.capacity=7,charBuffer.limit=7
        System.out.println(String.format("charBuffer.position=%s,charBuffer.capacity=%s,charBuffer.limit=%s", charBuffer.position(), charBuffer.capacity(), charBuffer.limit()));
        charBuffer.position(0);
        for (int i = 0; i < charBuffer.capacity(); i++) {
            //get方法使用的是utf-16be编码方式，utf-8和utf-16be并不是一种编码方式，此处就会出现了乱码
            System.out.print(charBuffer.get(i));
        }

    }
}

