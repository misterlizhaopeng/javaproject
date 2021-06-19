package nio_test.nio_socket.buffer;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_clear
 * @Deacription 清理缓冲区：这个清理
 * @Author LP
 * @Date 2021/6/5 17:31
 * @Version 1.0
 **/
public class Buffer_byteBuffer_put {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.putChar('a');//占用 2 个byte
        byteBuffer.putLong(10);//占用 8 个byte
        //position = 10,limit = 100
        System.out.println(String.format("position=%s,limit=%s", byteBuffer.position(), byteBuffer.limit()));
    }
}

