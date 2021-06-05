package nio_test.nio_socket;

import java.nio.CharBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_limit_equals_position
 * @Deacription position 的值和limit的值一样的时候，写入数据会出现异常，因为此位置是被限定住的
 * @Author LP
 * @Date 2021/6/5 16:47
 * @Version 1.0
 **/
public class Buffer_limit_equals_position {
    public static void main(String[] args) {
        char[] chars =new char[]{'1','b','c','d'};
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        System.out.println(String.format("position=%s,limit=%s",charBuffer.position(),charBuffer.limit()));

        charBuffer.limit(2);
        charBuffer.position(2);
        charBuffer.put("d");


    }
}

