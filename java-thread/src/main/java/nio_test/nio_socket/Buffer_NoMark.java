package nio_test.nio_socket;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer
 * @Deacription 没有定义mark，直接执行reset，会发送 InvalidMarkException 异常
 * @Author LP
 * @Date 2021/6/5 16:30
 * @Version 1.0
 **/
public class Buffer_NoMark {
    public static void main(String[] args) {
        byte [] bytes= new byte[]{1,2,2,3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.reset();


    }
}

