package nio_test.nio_socket.socket_communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName nio_test.nio_socket.socket_communication.TestReuseAddress2
 * @Deacription :
 * @Author LP
 * @Date 2021/8/8
 * @Version 1.0
 **/
public class TestReuseAddress2 {
    private static final String HOST_ADDRESS = "localhost";
    private static final Integer HOST_PORT = 1000;

    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(HOST_PORT);
        System.out.println("accept begin:");
        Socket socket = serverSocket.accept();
        System.out.println("accept end:");

        socket.close();
        serverSocket.close();


    }
}

