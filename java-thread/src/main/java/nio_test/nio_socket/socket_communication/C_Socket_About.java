package nio_test.nio_socket.socket_communication;

import org.junit.Test;

import java.io.IOException;
import java.net.*;

/**
 * @ClassName nio_test.nio_socket.socket_communication.C_Socket_About
 * @Deacription : Socket 类包含很多实用且能增加软件运行效率的 API 方法；
 *
 *                  1.bind/ connect / 端口生成时机
 *                      bind(SocketAddress bindpoint)  方法是将套接字绑定到本地，如果为null，则系统会随机分配一个空闲的端口和一个有效的本地地址来绑定；
 *                      注意！！！！：在Socket通信中，服务端、客户端都需要端口进行通信 ！！！！
 *                      在 ServerSocket 端，创建ServerSocket的时候，构造函数中或者bind的时候需要一个端口，而在客户端Socket中，直接通过构造函数（服务端地址+服务端port）
 *                          连接ServerSocket，而本地没有绑定本地地址，端口，其实Socket客户端是的端口号是存在的；
 *                      Socket 端在没有明显的bind，端口是随机被系统分配的，通过方法 bind 可以绑定到具体端口上；
 *                      客户端的bind方法需要在connect方法之前执行，因为连接前需要现在本地绑定具体的地址信息；
 *                      如果没有通过方法bind绑定本地地址和端口，在connect连接时候，本地端口号码会通过系统随机分配一个；
 *                      值得注意的核心思路就是：上面的注意！
 *
 *                  2.Socket‘s connect（）'s timeout 超时
 *                  3.获取远程端口和本地端口
 *                  4.获取本地地址 、本地socket 地址 / 获取远程地址 、远程socket 地址
 * @Author LP
 * @Date 2021/8/9
 * @Version 1.0
 **/
public class C_Socket_About {

    private final static String HOST_ADDRESS = "localhost";
    private final static String HOST_ADDRESS_2 = "172.20.10.9";
    private final static Integer HOST_PORT = 1000;


    /**
     * 获取本地地址 、本地socket 地址 -ServerSocket
     *
     */
    @Test
    public void test_04_2() throws IOException {
        Socket socket = new Socket();
        socket.bind(new InetSocketAddress(HOST_ADDRESS_2, 1004));
        socket.connect(new InetSocketAddress(HOST_ADDRESS_2, HOST_PORT));
        socket.close();
    }

    /**
     * 获取本地地址 、本地socket 地址 -ServerSocket
     *
     * socket.getLocalAddress(); 获取此socket 绑定的本地ip地址信息
     * socket.getLocalSocketAddress();获取此socket 绑定的端的的socket地址信息
     *
     * socket.getInetAddress(); 返回此 socket连接到的远程的ip地址信息，如果套接字socket未连接，发挥null；
     * socket.getRemoteSocketAddress();返回此 socket连接到的远程的socket 信息，如果套接字socket未连接，发挥null；
     *
     *
     *
     *  输出结果：
     *          A-服务器ip地址：=-84 20 10 9
     *          A-服务端端口=1000
     *          B-客户端ip地址：=-84 20 10 9
     *          B-客户端端口=1004
     */
    @Test
    public void test_04_1() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(HOST_ADDRESS_2,HOST_PORT));
        Socket socket = serverSocket.accept();
        InetAddress inetAddress = socket.getLocalAddress();
        //InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getLocalSocketAddress();

        System.out.print("A-服务器ip地址：=");
        byte[] address = inetAddress.getAddress();
        for (int j = 0; j < address.length; j++) {
            System.out.print(address[j] + " ");
        }
        System.out.println();
        System.out.println("A-服务端端口=" + ((InetSocketAddress) socket.getLocalSocketAddress()).getPort());


        // 返回此 socket连接到的远程的ip地址信息，如果套接字socket未连接，发挥null；
        inetAddress = socket.getInetAddress();
        // 返回此 socket连接到的远程的socket 信息，如果套接字socket未连接，发挥null；
        //InetSocketAddress remoteInetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

        System.out.print("B-客户端ip地址：=");
        byte[] address1 = inetAddress.getAddress();
        for (int i = 0; i < address1.length; i++) {
            System.out.print(address1[i] + " ");
        }
        System.out.println();
        System.out.println("B-客户端端口=" + ((InetSocketAddress) socket.getRemoteSocketAddress()).getPort());


        //关闭资源
        socket.close();
        serverSocket.close();
    }


    /**
     * 获取远程端口和本地端口 - Socket
     *      socket.getPort()：获取socket远程端的端口
     *      socket.getLocalPort()：获取socket本地端的端口
     */
    @Test
    public void test_03_2() throws IOException {
        //Socket socket = new Socket(HOST_ADDRESS_2, HOST_PORT);
        Socket socket = new Socket();
        socket.bind(new InetSocketAddress(HOST_ADDRESS_2,1002));
        socket.connect(new InetSocketAddress(HOST_ADDRESS_2,HOST_PORT));
        System.out.println("客户端的输出：");
        System.out.println("客户端的端口 socket.getLocalPort()=" + socket.getLocalPort());
        System.out.println("服务的的端口 socket.getPort()=" + socket.getPort());

        socket.close();
    }

    /**
     * 获取远程端口和本地端口 - ServerSocket
     *  socket.getPort()：获取socket远程端的端口
     *  socket.getLocalPort()：获取socket本地端的端口
     *
     */
    @Test
    public void test_03_1() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(HOST_ADDRESS_2,HOST_PORT));
        Socket socket = serverSocket.accept();
        System.out.println("服务端口输出：");
        System.out.println("服务端的端口 socket.getLocalPort()=" + socket.getLocalPort());
        System.out.println("客户端的端口 socket.getPort()=" + socket.getPort());
        //关闭资源
        socket.close();
        serverSocket.close();


    }

    /**
     * Socket‘s connect（）'s timeout 超时 -socket
     *
     *  特别注意的是，在socketbind的时候，SocketAddress的ip信息不要是 localhost ，因为这远程不能访问localhost；
     *  connect在windows系统下，不设置超时时间，或者给超时时间传值0 的情况下，默认超时时间为20s ，但是经过 linux 的测试，
     *      超时时间(没找到主机的时间)是 3s；
     */
    @Test
    public void test_02_1() throws IOException {
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
            socket.connect(new InetSocketAddress("1.1.1.1", HOST_PORT));
        } catch (Exception ex) {
            //java.net.ConnectException: Connection timed out: connect
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - beginTime);
            ex.printStackTrace();
        }
    }



    /**
     * bind/ connect / 端口生成时机 - Socket
     * @throws IOException
     */
    @Test
    public void test_01_2() throws IOException {
//        Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
        Socket socket = new Socket();
        socket.bind(new InetSocketAddress(HOST_ADDRESS,1001));//绑定本地地址
        socket.connect(new InetSocketAddress(HOST_ADDRESS,HOST_PORT));//连接远程服务端，优先于bind方法执行
        socket.close();
    }

    /**
     * bind/ connect / 端口生成时机 - ServerSocket
     * @throws IOException
     */
    @Test
    public void test_01_1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(HOST_PORT);
        Socket socket = serverSocket.accept();
        socket.close();
        serverSocket.close();
    }
}

