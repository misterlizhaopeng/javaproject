package nio_test.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;


/**
 *
 * 总结：
 * 1.两个地方是阻塞的：serverSocket.accept();socket.getInputStream().read(bytes);
 * 2.两个while循环在主线程中：下面代码第一个while循环是为了解决多个用户的问题，一个线程对应一个会话socket；第二个while是为了解决每个会话持续提供服务；
 *
 *
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        //服务端口为9000，客户端通过改端口找到当前服务
        ServerSocket serverSocket = new ServerSocket(9001);
        while (true) {
            System.out.println("等待连接...");
            // 阻塞
            Socket acceptSocket = serverSocket.accept();

            //执行到这里，说明有客户端连接了
            System.out.println("客户端连接了..");
            new Thread(() -> {
                System.out.println("开启新的线程...");
                try {
                    handler(acceptSocket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }

    public static void handler(Socket socket) throws IOException {
        System.out.println(String.format("thread id =%s,thread name=%s", Thread.currentThread().getId(), Thread.currentThread().getName()));
        //针对每个线程，进行持续响应
        while (true) {
            byte[] bytes = new byte[1024];
            System.out.println("准备read..");
            //阻塞：没数据可读的时候，阻塞着
            int read = socket.getInputStream().read(bytes);
            System.out.println("读完毕");
            String input = "";
            if (read != -1) {
                // 接收到客户端的数据
                input = new String(bytes, 0, read);
            }
            try {
                //如何判断socket是否有效，心跳检测可以实现
                // 写给客户端
                String responseStr = "HelloClient:[" + Thread.currentThread().getName() + "," + Thread.currentThread().getId() + "], 你传递的内容为：" + input;
                socket.getOutputStream().write(responseStr.getBytes());
                socket.getOutputStream().flush();

            }catch (SocketException ex){
                socket.close();
                System.out.println(ex.toString());
            }
        }
    }
}
