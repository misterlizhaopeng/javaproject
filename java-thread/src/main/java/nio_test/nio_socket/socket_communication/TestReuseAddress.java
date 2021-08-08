package nio_test.nio_socket.socket_communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName nio_test.nio_socket.socket_communication.TestReuseAddress
 * @Deacription :
 * @Author LP
 * @Date 2021/8/8
 * @Version 1.0
 **/
public class TestReuseAddress {
    private static final String HOST_ADDRESS = "localhost";
    private static final Integer HOST_PORT = 1000;

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * 当前测试服务端断开连接，服务端口是否被复用的情况 ，TestReuseAddress2 进行模拟是否可以复用的情况
         *
         *      不能被复用测试结果：tcp连接断开，端口不能被复用的时间段，别的程序复用就会抛如下错误：
         *
         *         【Exception in thread "main" java.net.BindException: 地址已在使用 (Bind failed)
         *          	at java.net.PlainSocketImpl.socketBind(Native Method)
         *          	at java.net.AbstractPlainSocketImpl.bind(AbstractPlainSocketImpl.java:513)
         *          	at java.net.ServerSocket.bind(ServerSocket.java:375)
         *          	at java.net.ServerSocket.<init>(ServerSocket.java:237)
         *          	at java.net.ServerSocket.<init>(ServerSocket.java:128)
         *          	at nio_test.nio_socket.socket_communication.TestReuseAddress2.main(TestReuseAddress2.java:21)
         *          】
         *
         * linux netstat -aon的 TIME_WAIT状态：
         *      tcp6       0      0 127.0.0.1:1000          127.0.0.1:7777          TIME_WAIT   timewait (54.73/0/0)
         *
         */
        Thread tServer = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(false);//测试服务端不允许当前端口在断开“停留若干时间”的时间段 被复用
                //serverSocket.setReuseAddress(true);//测试服务端允许当前端口在断开“停留若干时间”的时间段 被复用
                serverSocket.bind(new InetSocketAddress(HOST_ADDRESS, HOST_PORT));
                Socket socket = serverSocket.accept();

                Thread.sleep(1000);
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        tServer.start();
        Thread.sleep(500);


        /**
         * 当前测试客户端断开连接，客户端的端口是否被复用的情况
         */
        Thread tClient = new Thread(() -> {
            try {
                //Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
                Socket socket = new Socket();
                socket.setReuseAddress(false);
                socket.bind(new InetSocketAddress(7777));//客户端Sokcet 绑定当前客户端的 端口号
                socket.connect(new InetSocketAddress(HOST_ADDRESS,HOST_PORT));//客户端Socket 连接服务端的 socket 的信息
                System.out.println("client socket.getLocalPort()" + socket.getLocalPort());
                Thread.sleep(3000);
                socket.close();//关闭 socket，相当于关闭 tcp 的连接
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        tClient.start();
    }
}

