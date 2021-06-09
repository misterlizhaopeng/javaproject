package nio_test.nio_socket;

import java.nio.CharBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_flip
 * @Deacription flip()方法的作用核心就是：limit=position；position=0 ；收缩的意思
 * @Author LP
 * @Date 2021/6/5 19:08
 * @Version 1.0
 **/
public class Buffer_flip {
    public static void main(String[] args) {
        CharBuffer charBuffer = CharBuffer.allocate(20);
        charBuffer.put("12345");
        //position=5,limit=20，capacity=20
        System.out.println(String.format("position=%s,limit=%s，capacity=%s", charBuffer.position(), charBuffer.limit(), charBuffer.capacity()));
        //flip 收缩下：
        charBuffer.flip();
        //position=0,limit=5，capacity=20
        System.out.println(String.format("position=%s,limit=%s，capacity=%s", charBuffer.position(), charBuffer.limit(), charBuffer.capacity()));


    }
}

