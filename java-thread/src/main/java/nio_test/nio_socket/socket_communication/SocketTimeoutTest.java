package nio_test.nio_socket.socket_communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @ClassName nio_test.nio_socket.socket_communication.SocketTimeoutTest
 * @Deacription :
 * @Author LP
 * @Date 2021/8/11
 * @Version 1.0
 **/
public class SocketTimeoutTest {

    private final static String HOST_ADDRESS = "localhost";
    //    private final static String HOST_ADDRESS_2 = "172.20.10.9";
    private final static String HOST_ADDRESS_2 = "192.168.25.141";

    private final static Integer HOST_PORT = 1000;

    public static void main(String[] args) {
        Socket socket = new Socket();
        long beginTime=0;
        try {
            //特别注意的是，在socketbind的时候，SocketAddress的ip信息不要是localhost，因为这远程访问不到这里
            //socket.bind(new InetSocketAddress(HOST_ADDRESS,7777));
            socket.bind(new InetSocketAddress(HOST_ADDRESS_2, 7777));
            beginTime = System.currentTimeMillis();
            //6s 的超时时间，ip地址为1.1.1.1，访问不到，所以6s超时了
            //socket.connect(new InetSocketAddress("1.1.1.1", HOST_PORT), 6000);
            //socket在连接时，的超时时间，ip地址为1.1.1.1，访问不到，所以6s超时了
            socket.connect(new InetSocketAddress("192.168.25.11", HOST_PORT),0);
        } catch (Exception ex) {
//            java.net.ConnectException: Connection timed out: connect
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - beginTime);
            ex.printStackTrace();
        }

//        finally {
//            System.out.println("time end =" + System.currentTimeMillis());
//            System.out.println("over");
//        }


    }
}

