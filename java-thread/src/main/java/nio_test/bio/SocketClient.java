package nio_test.bio;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) throws IOException {

        // 确定指向服务端的socket
        Socket socket = new Socket("127.0.0.1", 9001);

        //客户端循环输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if ("Done".equals(input)) {
                //客户端输入完成，关闭socket，并且跳出循环
                socket.close();
                break;
            }

            //回车等空内容，进行下次获取输入的值
            if (Objects.isNull(input) || input.equals("")) {
                continue;
            }
            // 向服务端进行写操作
            //socket.getOutputStream().write("hello".getBytes());
            socket.getOutputStream().write(input.getBytes());
            socket.getOutputStream().flush();
            System.out.println("向服务端发送数据结束");


            byte[] bytes = new byte[1024];
            //接收服务端回传的数据
            int read = socket.getInputStream().read(bytes);
            String input_from_client = "";
            if (read != -1) {
                input_from_client = new String(bytes, 0, read);
            }
            System.out.println("接收到服务端的数据：" + input_from_client);

        }
        // 不能关闭socket，为了保持连接
        //socket.close();
    }
}
