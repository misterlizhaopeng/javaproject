package nio_test.nio_socket.buffer;

import java.nio.ByteBuffer;

/**
 * @ClassName nio_test.nio_socket.Buffer_limit
 * @Deacription limit  :limit 下标从0开始
 * @Author LP
 * @Date 2021/6/3 8:27
 * @Version 1.0
 **/
public class Buffer_limit {
    public static void main(String[] args) {
        byte[] b = {1, 2, 3, 4};
        ByteBuffer buffer = ByteBuffer.wrap(b);
        System.out.println(String.format("capacity=%s,limit=%s", buffer.capacity(), buffer.limit()));

        for (int i = 0; i < buffer.capacity(); i++) {
            byte b1 = buffer.get(i);
            System.out.println(b1);
        }

        //设置limit为索引为2
        buffer.limit(2);

        for (int i = 0; i < buffer.capacity(); i++) {
            byte b1 = buffer.get(i);
            System.out.println(b1);
        }

    }
}

