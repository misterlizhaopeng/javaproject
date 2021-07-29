package nio_test.nio_socket.socket_communication;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName nio_test.nio_socket.socket_communication.A_Socket_TCP_Communication
 * @Deacription : 基于TCP 的socket通信
 *                  1.测试ServerSocket的accept方法的阻塞性（见方法 test_01_1、 test_01_2）
 *                  2.通过ServerSocket 写一个简易的web服务器
 *                  3.测试Socket 中 InputStream 类的read方法的阻塞性
 *                  4.服务端向客户端传递字符串
 *
 * @Author LP
 * @Date 2021/7/29
 * @Version 1.0
 **/
public class A_Socket_TCP_Communication {
    private final static String HOST_ADDRESS = "localhost";
    private final static Integer HOST_PORT = 1000;


    /**
     * 服务端向客户端传递字符串-客户端 Socket
     *
     * @throws IOException
     */
    @Test
    public void test_04_2() throws IOException {
        System.out.println(String.format("client-准备连接:%s",System.currentTimeMillis()));
        Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
        System.out.println(String.format("client-准备结束:%s",System.currentTimeMillis()));

        char[] chars = new char[2];
        InputStream in = socket.getInputStream();
        InputStreamReader inReader = new InputStreamReader(in);
        int readLen = inReader.read(chars);
        while (readLen != -1){
            System.out.print(new String(chars,0,readLen));
            readLen = inReader.read(chars);
        }


        //关闭资源
        inReader.close();
        in.close();
        socket.close();
    }

    /**
     * 服务端向客户端传递字符串-ServerSocket
     *
     * @throws IOException
     */
    @Test
    public void test_04_1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(HOST_PORT);
        System.out.println(String.format("server-开始阻塞:%s",System.currentTimeMillis()));
        Socket socket = serverSocket.accept();
        System.out.println(String.format("server-结束阻塞:%s",System.currentTimeMillis()));
        OutputStream out = socket.getOutputStream();
        out.write("hello client，我是李朋".getBytes());

        //关闭资源
        out.close();
        socket.close();
        serverSocket.close();
    }


    /**
     * 测试Socket 中 InputStream 类的read方法的阻塞性-socket客户端
     *      测试思路：建立连接（就是new Socket(port)实例），不发送数据，具体当前测试方法的代码
     *
     *Socket中InputStream 对象的 read方法阻塞的原因：该read方法阻塞的原因就是：客户端没有发送数据到服务端，服务端一直在尝试读取客户端发来的数据，所以阻塞；
     */
    @Test
    public void test_03_2() throws IOException, InterruptedException {
        System.out.println(String.format("client-begine：%s", System.currentTimeMillis()));
        Socket socket = new Socket(HOST_ADDRESS,HOST_PORT);
        System.out.println(String.format("client-end：%s", System.currentTimeMillis()));
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 测试Socket 中 InputStream 类的read方法的阻塞性
     *      测试结果：
     *              server-accept阻塞开始：1627567224581
     *              server-accept阻塞结束：1627567255975
     *              InputStream-read 阻塞开始：1627567255976
     * Socket中InputStream 对象的 read方法阻塞的原因：该read方法阻塞的原因就是：客户端没有发送数据到服务端，服务端一直在尝试读取客户端发来的数据，所以阻塞；
     */
    @Test
    public void test_03_1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(HOST_PORT);
        System.out.println(String.format("server-accept阻塞开始：%s", System.currentTimeMillis()));
        Socket socket = serverSocket.accept();//具有阻塞性
        System.out.println(String.format("server-accept阻塞结束：%s", System.currentTimeMillis()));

        byte[] bytes= new byte[1024];
        InputStream in = socket.getInputStream();
        System.out.println(String.format("InputStream-read 阻塞开始：%s", System.currentTimeMillis()));
        int readLen = in.read(bytes);//把socket 的输入流内容读入到字节数据缓冲区中，该方法具有阻塞性，当前测试就是测试该read方法的阻塞性
        System.out.println(String.format("InputStream-read 阻塞结束：%s", System.currentTimeMillis()));

        in.close();
        socket.close();
        serverSocket.close();

    }

    /**
     * 通过ServerSocket 写一个简易的WEB 服务器
     *
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        ServerSocket serverSocket = new ServerSocket(HOST_PORT);//服务端的监听端口
        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();
        InputStreamReader reader_in = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(reader_in);
        String readStr;
        while (!"".equals(readStr=bufferedReader.readLine())) {
            System.out.println(readStr);
        }

        OutputStream out = socket.getOutputStream();
        out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        out.write("<html><body>Hello MISTER LP</body></html>".getBytes());

        in.close();
        out.close();
        socket.close();
        serverSocket.close();
    }


    /**
     * 测试ServerSocket的accept方法的阻塞性-Socket客户端访问
     *      new Socket("localhost", 8080);//localhost 代表服务器的地址，8080 代表服务器的端口号；
     *      第一个参数可以换成IP地址或者域名，如果使用域名，就会使用dns服务转换层ip地址，在访问具体的服务；
     *          如果使用错误的域名，则程序报错；
     *
     * @throws IOException
     */
    @Test
    public void test_01_2() throws IOException {
        System.out.println(String.format("client 连接准备：%s", System.currentTimeMillis()));
       // Socket socket = new Socket("localhost", 8080);//localhost 代表服务器的地址，8080 代表服务器的端口号
       // Socket socket = new Socket("www.csdn.net", 80);//使用域名访问csdn的服务端，可以成功访问
        Socket socket = new Socket("www.csdn.netxxxx", 80);

        System.out.println(String.format("client 连接结束：%s", System.currentTimeMillis()));
        socket.close();

    }

    /**
     * 测试ServerSocket的accept方法的阻塞性
     *      当运行此方法，程序会停止到serverSocket.accept();直到客户端Socket连接到这里，
     *          或者说 ServerSocket 监听（接受）到有客户端Socket 建立连接，并返回 Socket 对象之后，继续往下执行，结合 test_01_2方法的代码测试，可以看出结果；
     *      ServerSocket 一般代表服务端； Socket 一般代表客户端；
     *
     * @throws IOException
     */
    @Test
    public void test_01_1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println(String.format("server阻塞开始：%s", System.currentTimeMillis()));
        Socket socket = serverSocket.accept();
        System.out.println(String.format("server阻塞结束：%s", System.currentTimeMillis()));
        socket.close();//
    }
}

