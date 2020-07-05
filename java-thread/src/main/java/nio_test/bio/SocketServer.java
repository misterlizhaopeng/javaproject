package nio_test.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        //服务端口为9000，客户端通过改端口找到当前服务
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("等待连接...");
            // 阻塞
            Socket acceptSocket = serverSocket.accept();

            //执行到这里，说明有客户端连接了
            System.out.println("客户端连接了..");
            new Thread(() -> {
                System.out.println("新的的任务...");
                try {
                    handler(acceptSocket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }

    public static void handler(Socket socket) throws IOException {
        System.out.println("thread id = " + Thread.currentThread().getId());
        byte[] bytes = new byte[1024];
        System.out.println("准备read..");
        //阻塞：没数据可读的时候，阻塞着
        int read = socket.getInputStream().read(bytes);
        System.out.println("读完毕");

        if (read != -1) {
            System.out.println("接收到客户端的数据：" + new String(bytes, 0, read));
            System.out.println("thread id = " + Thread.currentThread().getId());
        }

        //写给客户端
        socket.getOutputStream().write("HelloClient".getBytes());
        socket.getOutputStream().flush();


    }


}
