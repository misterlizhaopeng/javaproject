package nio_test.nio_socket;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_clear
 * @Deacription 清理缓冲区：这个清理
 * @Author LP
 * @Date 2021/6/5 17:31
 * @Version 1.0
 **/
public class Buffer_clear {
    public static void main(String[] args) {
        byte[] bytes=new byte[]{1,2,3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.position(1);

        System.out.println(String.format("position=%s,limit=%s",byteBuffer.position(),byteBuffer.limit()));
        //清理缓冲区:一般应用场景：在向缓冲区写数据之前，一般清理下，其实这个方法不能真实地清楚数据，
        //而只是调整了缓冲区的最初是的状态（只是调整了mark、position、limit、capacity的值）
        byteBuffer.clear();
        System.out.println(String.format("position=%s,limit=%s",byteBuffer.position(),byteBuffer.limit()));

    }
}

