package nio_test.nio_socket.socket_communication;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

/**
 * @ClassName nio_test.nio_socket.socket_communication.B_ServerSocket_About
 * @Deacription :   关于ServerSocket 的使用
 *                      ServerSocket中有很多方法，熟悉这些方法是掌握Socket的基础；
 *
 *
 *
 *                  1.accept() 方法与 timeout 超时
 *                  2.构造函数参数 backlog 的意义
 *                  3.ServerSocket 构造方法的异同
 *
 * @Author LP
 * @Date 2021/8/4
 * @Version 1.0
 **/
public class B_ServerSocket_About {
    private final static String HOST_ADDRESS = "localhost";
    private final static Integer HOST_PORT = 1000;

    /**
     *  ServerSocket 构造方法的异同 - Socket
     *
     */
    @Test
    public  void test_03_2() throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        for (int i = 0; i < 100; i++) {
            Socket socket = new Socket(inetAddress, HOST_PORT);
            System.out.println("client 发起连接的次数;"+(i+1));
        }
    }

    /**
     *  ServerSocket 构造方法的异同 -ServerSocket
     *      ServerSocket serverSocket = new ServerSocket(HOST_PORT);
     *      ServerSocket serverSocket = new ServerSocket(HOST_PORT, 50);
     *      上面两种情况：是客户端可以使用服务端的任意ip地址连接到ServerSocket对象中；
     *
     *      ServerSocket serverSocket = new ServerSocket(HOST_PORT, 50, InetAddress.getLocalHost());
     *      这种情况：是客户端Socket在创建socket对象连接服务端的时候，所连接的服务端ip 地址，必须是ServerSocket 构造函数的第三个参数相同的 IP 地址，否则就会出现异常；
     *
     *
     *      此种情况，也就是这样创建一个ServerSocket对象 new ServerSocket(HOST_PORT, 60, inetAddress);然后让程序停止15s之后继续，在15s之内，发起test_03_2的客户端的100个
     *          连接的请求，客户端可以打印出60个连接是成功的，大于60之后的链接，就会出现【java.net.ConnectException: Connection refused: connect】异常；
     *          15s过后，服务端也只能处理这种情况下的60个连接，然后执行到accept() 方法继续等待，这里再次说明了【构造函数参数 backlog 的意义】backlog的原理；
     *
     */
    @Test
    public  void test_03_1() throws IOException, InterruptedException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        ServerSocket serverSocket = new ServerSocket(HOST_PORT, 60, inetAddress);
        Thread.sleep(15000);//停止程序15s之后，继续

        for (int i = 0; i < 100; i++) {
            System.out.println("accept begin:"+(i+1));
            Socket socket = serverSocket.accept();
            System.out.println("accept begin:"+(i+1));
        }
        serverSocket.close();
    }


    /**
     * 获取当前jvm的进程id
     * @throws InterruptedException
     */
    @Test
    public void pidTest() throws InterruptedException {
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf('@');
        if(indexOf > 0){
            pid = pid.substring(0,indexOf);
        }
        System.out.println("当前JVM进程号为:" + pid);
        Thread.sleep(Integer.MAX_VALUE);
    }


    /**
     * 构造函数参数 backlog 的意义- Socket
     *
     * @throws IOException
     */
    @Test
    public  void test_02_2() throws IOException {
        for (int i = 0; i < 100; i++) {
            Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
            System.out.println("client 发起连接次数："+(i+1));
        }

    }

    /**
     * 构造函数参数 backlog 的意义 - ServerSocket
     *      ServerSocket 构造函数的第二个参数backlog的意义为：允许接受客户端连接请求的个数。
     *          客户端有很多连接进入操作系统中，将这些连接放入 os 的队列中，当执行 accept（）方法时，允许客户端请求的个数要取决于backlog参数；
     *      所以，传入第二个参数backlog 的作用为：设置等待最大队列的长度，如果队列已满，则拒绝该连接；
     *          backlog 的值必须为大于 0 的值，如果传递的值小于或者等于 0，则使用默认值 50 ；
     *      如果第一个参数port 为 0 ，表示服务端将自动分配空闲的端口号；
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public  void test_02_1() throws IOException, InterruptedException {
        //backlog默认值 50
        ServerSocket serverSocket = new ServerSocket(HOST_PORT,3);//不设置backlog，[默认为50]，表示客户端只能支持50个连接同时到队列中，大于该值则抛异常
        Thread.sleep(15000);//停止15s 的时间，等着让客户端的链接先进入os的队列中，
                                    // 如果客户端的链接个数大于服务端设置的backlog的数值，
                                    // 客户端就会抛被拒绝的异常：java.net.ConnectException: Connection refused: connect
        for (int i = 0; i < 100; i++) {
            System.out.println("accept begin:"+(i+1));
            Socket socket = serverSocket.accept();
            System.out.println("accept begin:"+(i+1));
        }

        serverSocket.close();

    }



    /**
     *  accept()方法与timeout 超时
     *  与 test_01_1 一起测试，在8s内启动客户端，就能正常与服务端简历连接，这样服务端就不会超时；
     */
    @Test
    public  void test_01_2() throws IOException {
        System.out.println("begin: client - start");
        Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
        System.out.println("begin: client - end");

    }

    /**
     *  accept()方法与timeout 超时
     *      accept();该方法的作用就是：侦听并并接受套接字的连接；此方法在连接之前一直处于阻塞状态；
     *      setSoTimeout() 方法通过设置超时时间，启用或者禁用 SocketOptions.SO_TIMEOUT，以毫秒为单位；
     *          通过此方法，可以设置超时时间，如果超过了超时时间，会报告 java.net.SocketTimeoutException异常，
     *          此时，ServerSocket对象有效，可以通过try-catch设置，继续使用ServerSocket对象，继续使用accept()方法；
     *      SocketOptions.SO_TIMEOUT在 accept() 调用之前或者说被阻塞之前调用被启用才有效，该值必须大于 0 ，如果等于
     *          0 表示，一直处于阻塞状态；
     *      ServerSocket.getSoTimeout()方法获取SocketOptions.SO_TIMEOUT在的值，返回 0 表示禁用了该选项，即无穷大超时；
     */
    @Test
    public  void test_01_1() {

        try {
            ServerSocket serverSocket = new ServerSocket(HOST_PORT);
            System.out.println(serverSocket.getSoTimeout());
            serverSocket.setSoTimeout(8000);//设置超时时间为 8 s
            System.out.println(serverSocket.getSoTimeout());
            System.out.println();

            System.out.println("begin:"+System.currentTimeMillis());
            Socket socket = serverSocket.accept();
            System.out.println("begin:"+System.currentTimeMillis());

        }catch (IOException ex){
            ex.printStackTrace();
            System.out.println("catch:"+System.currentTimeMillis());

        }




    }


}

