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
 *                  5.允许调用多次write方法进行写入操作
 *                  6.实现服务端、客户端的多次来往通信
 *
 * @Author LP
 * @Date 2021/7/29
 * @Version 1.0
 **/
public class A_Socket_TCP_Communication {

    private final static String HOST_ADDRESS = "localhost";
    private final static Integer HOST_PORT = 1000;

    /**
     * 前面实现了 1 次通信，如何实现服务端和客户端多次来往的长连接通信呢 ？- Socket
     * @throws IOException
     */
    @Test
    public void test_06_2 () throws IOException {
        Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        //输出开始
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        String outStr = "A-你好，服务端\n";
        String outStr2 = "B-你好，服务端\n";
        int outLen = (outStr+outStr2).getBytes().length;
        objectOutputStream.writeInt(outLen);
        objectOutputStream.flush();

        objectOutputStream.write(outStr.getBytes());
        objectOutputStream.write(outStr2.getBytes());
        objectOutputStream.flush();
        //输出结束


        //输入开始 从socket中读取内容到当前数组缓冲区，打印之
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        int readInt = objectInputStream.readInt();
        byte [] bytes = new byte[readInt];
        objectInputStream.readFully(bytes);
        System.out.println(new String(bytes));
        //输入结束

        //输出开始
        outStr = "C-你好，服务端\n";
        outStr2 = "D-你好，服务端\n";
        outLen = (outStr + outStr2).getBytes().length;
        objectOutputStream.writeInt(outLen);
        objectOutputStream.flush();
        objectOutputStream.write(outStr.getBytes());
        objectOutputStream.write(outStr2.getBytes());
        objectOutputStream.flush();
        //输出结束

        //输入开始
        readInt = objectInputStream.readInt();
        bytes = new byte[readInt];
        objectInputStream.readFully(bytes);
        System.out.println(new String(bytes));
        //输入结束


        objectOutputStream.close();
        out.close();
        socket.close();
    }

    /**
     * 前面实现了 1 次通信，如何实现服务端和客户端多次来往的长连接通信呢 ？-ServerSocket
     */
    @Test
    public void test_06_1() throws IOException {

        ServerSocket serverSocket = new ServerSocket(HOST_PORT);
        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();
        //输入开始
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        int byteLen = objectInputStream.readInt();
        byte[]bytes=new byte[byteLen];
        objectInputStream.readFully(bytes);//从流中读取内容到字节类型的数组缓冲区
        System.out.println(new String(bytes));//打印输出所有的字节数组中的内容
        //输入结束

        //输出开始
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        String outStr="1-我是李四，请叫我张三\n";
        String outStr2= "2-ni name is 设么？\n";
        int outLen = (outStr+outStr2).getBytes().length;//字节长度
        objectOutputStream.writeInt(outLen);
        objectOutputStream.flush();//写入socket通道中

        objectOutputStream.write(outStr.getBytes());
        objectOutputStream.write(outStr.getBytes());
        objectOutputStream.flush();
        //输出结束

        //输入开始
        byteLen = objectInputStream.readInt();//读取流中的字节长度，用于创建数组缓冲区 ，然后读取所有长度的字节到数组缓冲区中，然后打印读取的字节转为字符串的内容
        bytes = new byte[byteLen];
        objectInputStream.readFully(bytes);
        System.out.println(new String(bytes));
        //输入结束


        //输出开始
        outStr = "3-what's your name?你的名称叫什么？\n";
        outStr2 = "4-客户你好\n";
        outLen = (outStr + outStr2).getBytes().length;
        objectOutputStream.writeInt(outLen);
        objectOutputStream.flush();
        objectOutputStream.write(outStr.getBytes());
        objectOutputStream.write(outStr2.getBytes());
        objectOutputStream.flush();
        //输出结束

        //关闭资源
        in.close();
        socket.close();
        serverSocket.close();
    }


    /**
     * 允许调用多次write方法进行写入操作-client(Socket)
     *      服务端不执行while循环的条件是：客户端执行out.close()方法时，代表了到达流的结尾，不在传输数据（意思就是什么情况下，服务端停止执行while中的读操作，就是客户端关闭输出流的操作，也就是out.close()被调用）
     */
    @Test
    public void test_05_2() throws IOException, InterruptedException {

        System.out.println(String.format("client连接准备：%s", System.currentTimeMillis()));
        Socket socket = new Socket(HOST_ADDRESS, HOST_PORT);
        System.out.println(String.format("client连接结束：%s", System.currentTimeMillis()));

        //向socket中写入字节流的对象 OutputStream
        OutputStream out = socket.getOutputStream();
        out.write("1-abcdeffasdfd".getBytes());
        Thread.sleep(2000);
        out.write("2-AFDFDFDA".getBytes());
        Thread.sleep(1000);
        out.write("3-233214312".getBytes());
        Thread.sleep(1000);
        out.write("4-233214312".getBytes());


        System.out.println(String.format("client 关闭资源-开始：%s", System.currentTimeMillis()));
        //关闭资源
        out.close();
        socket.close();
        System.out.println(String.format("client 关闭资源-结束：%s", System.currentTimeMillis()));
    }

    /**
     * 允许调用多次write方法进行写入操作-server(ServerSocket)
     *      服务端不执行while循环的条件是：客户端执行out.close()方法时，代表了到达流的结尾，不在传输数据（意思就是什么情况下，服务端停止执行while中的读操作，就是客户端关闭输出流的操作，也就是out.close()被调用）
     */
    @Test
    public void test_05_1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(HOST_PORT);
        System.out.println(String.format("接受请求-阻塞开始-%s", System.currentTimeMillis()));
        Socket socket = serverSocket.accept();
        System.out.println(String.format("接受请求-阻塞结束-%s", System.currentTimeMillis()));

        InputStream in = socket.getInputStream();
        InputStreamReader inReader = new InputStreamReader(in);
        char[] chars= new char[100];//10个字符的长度的数组缓冲区，因为我想让inReader对象一次最多读取10个字符，就这样
        System.out.println(String.format("Server-begin-start", System.currentTimeMillis()));
        int readLen = inReader.read(chars);//返回值为读取的字符长度
        System.out.println(String.format("Server-begin-end", System.currentTimeMillis()));
        while (readLen != -1) {
            String inStr = new String(chars, 0, readLen);//从数组缓冲区中读取【被读取】长度的字符，并转换为字符串用于输出
            System.out.println(inStr);
            //表示如果readLen不为-1，表示继续读取socket中的字节流为字符流，直至读到-1长度的字符个数，结束读取工作
            readLen = inReader.read(chars);
        }
        //关闭资源
        in.close();
        socket.close();
        serverSocket.close();
    }

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

