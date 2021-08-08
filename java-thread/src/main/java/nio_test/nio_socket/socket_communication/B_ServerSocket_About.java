package nio_test.nio_socket.socket_communication;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.*;
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
 *                  4.绑定到指定的 Socket 地址 以及 指定 backlog 参数的值
 *                  5.验证windows 7 中的backlog中的最大值为200；
 *                  6.获取本地SocketAddress对象，以及本地端口
 *                  7.InetSocketAddress  类的使用
 *                  8.关闭获取关闭状态
 *                  9.判断socket的绑定状态
 *                  10.获取serversocket的绑定的ip地址信息
 *                  11.socket 选项：ReuseAddress
 *                      在关闭TCP连接时，该连接在关闭之后的一段时间内保持超时状态（通常称为TIME_WAIT或者2MSL等待状态）。
 *                      对于使用已知套接字地址或者端口号的程序来说，如果存在超时状态的链接（ip地址和端口），则应用程序可能不能将套接字绑定到所需的
 *                      SocketAddress上。
 *                      如果想让套接字绑定到该等待状态的链接地址上，可以通过方法 Socket.setReuseAddress(true); 实现，
 *                          重用端口地址可以提升端口的使用效率，用较少的端口完成更多的功能；
 *
 *                      什么是 TIME_WAIT 状态？服务端或者客户端建立TCP链接之后，主动断开的一端，就会进入 TIME_WAIT 状态，再“ 停留若干时间 ”，再进入 closed 状态；
 *                      在linux系统中，当在“停留若干时间”时，应用程序可以复用TIME_WAIT 的端口，这样可以提升端口的利用率；
 *
 *                      在linux 系统中进行测试
 *                          在linux 发行版CentOS中，默认允许端口复用；：
 *                          测试服务端（ServerSocket端）端口复用的情况：是通过文件TestReuseAddress 、TestReuseAddress2 在linux 下面进行测试的，执行完了TestReuseAddress，然后执行TestReuseAddress2 即可完成测试；
 *                          测试客户端（Socket端）端口复用的情况：是通过文件TestReuseAddress3在linux下面进行测试的，重复java 执行当前文件即可完成测试；
 *
 *                  12.socket 选项：ReceiveBufferSize
 *
 *
 * @Author LP
 * @Date 2021/8/4
 * @Version 1.0
 **/
public class B_ServerSocket_About {
    private final static String HOST_ADDRESS = "localhost";
    private final static Integer HOST_PORT = 1000;


    /**
     * socket 选项：ReceiveBufferSize - Socket
     * @throws IOException
     */
    @Test
    public  void test_12_2 () throws IOException {
        Socket socket = new Socket();
        System.out.println("client begin socket.getReceiveBufferSize() = " + socket.getReceiveBufferSize());
        socket.connect(new InetSocketAddress(HOST_ADDRESS,HOST_PORT));
        //socket.connect(new InetSocketAddress("192.168.25.141",9916));
        System.out.println("client end socket.getReceiveBufferSize() = " + socket.getReceiveBufferSize());
        OutputStream out = socket.getOutputStream();//socket输出对象，也可以理解为通过该字节流对象想socket中写入数据，传输到另一端
        for (int i = 0; i < 100; i++) {
            String outStr="1111111111111111111111111111111111111111111111" +
                    "22222222222222222222222222222222222222222" +
                    "333333333333333333333333333333333333" +
                    "444444444444444444444444444444444" +
                    "55555555555555555555555555555555555555555" +
                    "66666666666666666666666666666666666" +
                    "92392390r8t89wq8req89wqhfkfasdkl;fasdladsf" +
                    "777777777777777777777777777777777777";
            out.write(outStr.getBytes());
        }
        out.write("end!".getBytes());
        out.close();
//        socket.close();//字节流对象关闭，同时会关闭socket，所以此处注释了
    }

    /**
     * socket 选项：ReceiveBufferSize - ServerSocket
     *
     * ReceiveBufferSize 的功能介绍：设置内部套接字接受缓冲区的大小和设置远程同位体的TCP 接受窗口大小；
     *
     *  serverSocket.setReceiveBufferSize(103);
     *      //更改服务端的接受缓冲区的大小，高速客户端的窗口大小为103，窗口大小为：传输报文数据的len的值，可以通过wireshark监视得到
     *
     *  注意：对于客户端，SocketOptions.SO_RCVBUF选项 必须在connect之前设置，对于服务端，SocketOptions.SO_RCVBUF 选项必须在bind之前设置
     * @throws IOException
     */
    @Test
    public  void test_12_1 () throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        System.out.println("1-serverSocket.getReceiveBufferSize() = " + serverSocket.getReceiveBufferSize());//输出结果：serverSocket.getReceiveBufferSize() = 8192
        serverSocket.setReceiveBufferSize(103);//更改服务端的接受缓冲区的大小，高速客户端的窗口大小为103，窗口大小为：传输报文数据的len的值，可以通过wireshark监视得到
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

    /**
     * 获取serversocket的绑定的ip 地址信息
     *   方法 getInetAddress() 获取IP地址信息；
     *
     * @throws IOException
     */
    @Test
    public  void test_10 () throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(HOST_ADDRESS,HOST_PORT));
        InetAddress inetAddress = serverSocket.getInetAddress();
        System.out.println(inetAddress.getHostAddress());
    }


    /**
     * 判断socket的绑定状态
     *
     * @throws IOException
     */
    @Test
    public  void test_09_1 () throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        System.out.println("1- bind stat = "+ serverSocket.isBound());//判断 socket 的绑定状态
        serverSocket.bind(new InetSocketAddress("www.ssbaiddx.com",HOST_PORT));//这个地址不能解析，所以会抛出未解析的异常，不能解析IP地址被绑定
        System.out.println("2- bind stat = "+ serverSocket.isBound());//判断 socket 的绑定状态


//        serverSocket.bind(new InetSocketAddress(HOST_ADDRESS,HOST_PORT));
//        System.out.println("2- bind stat = "+ serverSocket.isBound());//判断 socket 的绑定状态


    }


    /**
     * serverSocket.close() 关闭套接字；
     *
     * 在accept（）方法中，所有当前阻塞的线程都会抛出SocketException 的异常；
     * 如果此套接字有一个与之关联的通道，则关闭通道；
     *
     *
     * @throws IOException
     */
    @Test
    public  void test_08 () throws IOException {
        ServerSocket serverSocket = new ServerSocket(HOST_PORT);
        System.out.println("1-serverSocket.isClosed() = " + serverSocket.isClosed());//false ，表示套接字未关闭
        serverSocket.close();
        System.out.println("2-serverSocket.isClosed() = " + serverSocket.isClosed());//true ，表示关闭了套接字


    }

    /**
     * InetSocketAddress  类的使用 - Socket
     */
    @Test
    public  void test_07_2 () throws IOException {
        System.out.println("socket client begin");
        Socket socket = new Socket("localhost", HOST_PORT);
        System.out.println("socket client end");
    }

    /**
     *  InetSocketAddress  类的使用 - ServerSocket
     *
     *      InetSocketAddress 实现IP 套接字地址（IP + port）或者（主机名 + port） ；
     *      inetSocketAddress.getHostName() 方法返回主机名称；值得注意的是：如果地址使用IP字面量创建的，则此方法可能会触发名称服务反向查找，也就是访问DNS服务，通过ip地址查找对应的域名；
     *      inetSocketAddress.getHostString() 方法返回主机名称或者主机的ip地址；注意，如果同一个InetSocketAddress下先调用方法getHostName()
     *          在调用方法getHostString()，那么方法 getHostString() 会返回主机名称；
     *      inetSocketAddress1.getAddress() 方法：获取 IP地址的InetAddress对象;
     *      创建未解析的套接字地址;
     *
     *
     */
    @Test
    public  void test_07_1 () throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName("172.20.10.9"), HOST_PORT);
        System.out.println(inetSocketAddress.getHostName());//同一个对象InetSocketAddress的实例下，此方法会影响getHostString()方法的值，并且getHostString()方法返回主机名；
        System.out.println(inetSocketAddress.getHostString());

        InetSocketAddress inetSocketAddress1 = new InetSocketAddress(InetAddress.getByName("172.20.10.9"), HOST_PORT);
        System.out.println(inetSocketAddress1.getHostString());
        System.out.println(inetSocketAddress1.getHostName());//同一个对象InetSocketAddress的实例下，这样，此方法就不会影响getHostString()方法的值了；

        InetAddress inetAddress = inetSocketAddress1.getAddress();//获取 IP地址的InetAddress对象
        System.out.println("inetAddress=" + inetAddress);

        //创建未解析的套接字地址
        InetSocketAddress inetSocketAddress2 = new InetSocketAddress("www.baidu.com", 80);


        serverSocket.bind(inetSocketAddress);
        System.out.println("server begin");
        serverSocket.accept();
        System.out.println("server end");
    }
    /**
     * 获取本地SocketAddress对象，以及本地端口
     *
     *      InetSocketAddress 代表 Socket 的IP地址 ，InetAddress 代表 IP 地址
     *
     */
    @Test
    public  void test_06() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        System.out.println("new ServerSocket() 无参构造函数端口为:" + serverSocket.getLocalPort());
        serverSocket.bind(new InetSocketAddress(HOST_ADDRESS,HOST_PORT));
        System.out.println("调用完毕bind方法后的端口是：" + serverSocket.getLocalPort());

        InetSocketAddress socketAddress = (InetSocketAddress) serverSocket.getLocalSocketAddress();
        System.out.println("socketAddress.getHostName() = " + socketAddress.getHostName());
        System.out.println("socketAddress.getHostString() = " + socketAddress.getHostString());
        System.out.println("socketAddress.getAddress() = " + socketAddress.getAddress());
        System.out.println("socketAddress.getPort() = " + socketAddress.getPort());
        serverSocket.close();



    }

    /**
     * 验证windows 7 中的backlog中的最大值为200 ； - Socket
     * @throws IOException
     */
    @Test
    public  void test_05_2() throws IOException {
        for (int i = 0; i < 5000; i++) {
            Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
            System.out.println("client send request: "+ (i+1));

        }

    }

    /**
     * 验证windows 7 中的backlog 中的最大值为200 ； - ServerSocket
     *
     *      验证backlog 为200个，其实就是在serverSocket端的accept()方法接受socket之前停止，客户端能正常创建多少个socket，也就是有多少客户端的socket可以
     *      进入os的等待队列，windows 7 中最大值为200，因为此实现证明了：
     *              服务端创建ServerSocket 20s之内，客户端socket创建了 5000个socket，但到201 个的之后，就报异常了；
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public  void test_05_1() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(HOST_PORT),Integer.MAX_VALUE);// 对backlog设置整型 最大的的值；
        Thread.sleep(20000);//设置20s的时间
        serverSocket.close();

    }


    /**
     * 绑定到指定的 Socket 地址 以及 指定 backlog 参数的值 - Socket
     */
    @Test
    public  void test_04_2() throws IOException {

        System.out.println("client request begin");
        Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
        System.out.println("client request end");



    }
    /**
     * 绑定到指定的 Socket 地址 以及 指定 backlog 参数的值 - ServerSocket
     *
     * a.对于serverSocket.bind(null);绑定了一个空地址，那么系统将会挑选一个临时的端口和一个有效的本地地址来绑定套接字；
     * b.对于serverSocket.bind()的参数：SocketAddress ，SocketAddress 是一个抽象类，代表Socket地址，而对于InetAddress代表 IP 地址；
     * c.对于 new InetSocketAddress() ，存在三个构造方法
     *      1.只有端口，其中当前的IP地址为通配符地址，端口号的有效范围：0~65535之间。端口号传递 0 ，代表在bind操作中 os 会随机挑选一个端口；
     *      2.有hostname，端口号，根据主机名和端口号创建套接字地址。端口号的有效范围：0~65535之间。端口号传递 0 ，代表在bind操作中 os 会随机挑选一个端口；
     *      3.有InetAddress，端口号，根据ip地址和端口号创建套接字地址。端口号的有效范围：0~65535之间。端口号传递 0 ，代表在bind操作中 os 会随机挑选一个端口；
     *
     * d.backlog 的意思和直接new ServerSocket()里面 的backlog 意思完全一致，这里就不过多介绍；
     * @throws IOException
     */
    @Test
    public  void test_04_1() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(HOST_PORT));

        System.out.println("server start accept()");
        serverSocket.accept();
        System.out.println("server end accept()");


    }



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

