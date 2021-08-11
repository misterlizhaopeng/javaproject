package nio_test.nio_socket.socket_communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName nio_test.nio_socket.socket_communication.ServerSocketTest
 * @Deacription :
 * @Author LP
 * @Date 2021/8/8
 * @Version 1.0
 **/
public class ServerSocketTest {
    private static final String HOST_ADDRESS = "192.168.25.141";
    private static final int HOST_PORT = 9916;


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket();
        System.out.println("1-serverSocket.getReceiveBufferSize() = " + serverSocket.getReceiveBufferSize());//输出结果：serverSocket.getReceiveBufferSize() = 8192
        serverSocket.setReceiveBufferSize(67);
        System.out.println("2-serverSocket.getReceiveBufferSize() = " + serverSocket.getReceiveBufferSize());
        serverSocket.bind(new InetSocketAddress(HOST_ADDRESS,HOST_PORT));//将服务端的socket绑定到当前地址（检查绑定socket地址）
        Socket socket = serverSocket.accept();
        InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
        System.out.println("remoteSocketAddress.getHostString() = " + remoteSocketAddress.getHostString());
        System.out.println("remoteSocketAddress.getHostName() = " + remoteSocketAddress.getHostName());
        InputStream in = socket.getInputStream();
        InputStreamReader inReader = new InputStreamReader(in);
        char[] chars = new char[1024];
        int readLen = inReader.read(chars);
        while (readLen !=-1){
            System.out.println(new String(chars,0,readLen));
            readLen = inReader.read(chars);
        }

        //关闭资源
        inReader.close();
        in.close();
//        socket.close();//字节流对象关闭，同时会关闭socket，所以此处注释了
        serverSocket.close();

    }
}

