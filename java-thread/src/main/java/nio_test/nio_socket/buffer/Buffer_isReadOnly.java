package nio_test.nio_socket.buffer;
import org.junit.Test;

import java.nio.CharBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_readonly
 * @Deacription TODO
 * @Author LP
 * @Date 2021/6/5 17:05
 * @Version 1.0
 **/
public class Buffer_isReadOnly {
    @Test
    public void test(){
        char[] chars =new char[]{'1','b','c','d'};
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        System.out.println(String.format("isReadOnly=%s",charBuffer.isReadOnly()));


    }
}

