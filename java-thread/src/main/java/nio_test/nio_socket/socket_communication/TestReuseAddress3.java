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
public class TestReuseAddress3 {
    private static final String HOST_ADDRESS = "localhost";
    private static final Integer HOST_PORT = 1000;

    public static void main(String[] args) throws IOException, InterruptedException {

        Thread tServer = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(true);//测试服务端允许当前端口在断开“停留若干时间”的时间段 被复用
                serverSocket.bind(new InetSocketAddress(HOST_ADDRESS, HOST_PORT));
                Socket socket = serverSocket.accept();
                Thread.sleep(3000);//延迟服务端断开
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
         * 当前测试客户端断开连接，客户端的端口是否被复用的情况，测试方法，指向当前方法，在执行一遍即可出现端口复用的问题
         *
         * 测试结果：【
         *              java.net.BindException: 地址已在使用 (Bind failed)
         *              	at java.net.PlainSocketImpl.socketBind(Native Method)
         *              	at java.net.AbstractPlainSocketImpl.bind(AbstractPlainSocketImp
         *              	at java.net.Socket.bind(Socket.java:661)
         *              	at nio_test.nio_socket.socket_communication.TestReuseAddress3.l
         *              	at java.lang.Thread.run(Thread.java:748)
         * 】
         * netstat -aon TIME_WAIT 状态：
         *  tcp6       0      0 127.0.0.1:7777          127.0.0.1:1000          TIME_WAIT   timewait (46.30/0/0)
         *
         */
        Thread tClient = new Thread(() -> {
            try {
                //Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
                Socket socket = new Socket();
                //socket.setReuseAddress(false);
                socket.setReuseAddress(true);
                socket.bind(new InetSocketAddress(7777));//客户端Sokcet 绑定当前客户端的 端口号
                socket.connect(new InetSocketAddress(HOST_ADDRESS,HOST_PORT));//客户端Socket 连接服务端的 socket 的信息
                System.out.println("client socket.getLocalPort()" + socket.getLocalPort());
                socket.close();//关闭 socket，相当于关闭 tcp 的连接
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        tClient.start();
    }
}

