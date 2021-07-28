package nio_test.nio_socket.deviceofnet;
import io.netty.util.internal.MacAddressUtil;
import org.junit.Test;
import sun.net.util.IPAddressUtil;
import sun.security.x509.IPAddressName;

import javax.lang.model.util.ElementScanner6;
import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName nio_test.nio_socket.deviceofnet.Network_01_NetworkInterface_Profile
 * @Deacription : 认识NetworkInterface 工具类：该接口提供访问网卡的信息，比如 网卡名称、ip、子网掩码 等；
 *                       NetworkInterface.getNetworkInterfaces() 的作用是取得 NetworkInterface 对象，返回该机器上的所有接口；
 *                       类 NetworkInterface 对象 的方法见测试方法test_01;
 *                       值得注意的是localhost 和 127.0.0.1 的区别，localhost 只是一个域名，有hosts文件解析为127.0.0.1 ；
 *                       测试方法test_01输出多组信息，代表存在多个网络接口，包括回环地址的虚拟设备以及真实的物理网卡，如下：1.获取网络接口的基本信息；
 *                       1.获取网络接口的基本信息
 *                          测试结论：
 *                                  1.网络设备的索引不是连续的 ；
 *                                  2.isLoopback针对lo设备返回true，其他设备返回false，因为系统中只有一个回环地址；
 *                                  3.networkInterface.isUp() 返回true，表示已开启并正常工作，比如手机无线网，连接，这个接口就正常，如果断开，就为false；
 *                                  4.在os为windows的 网络设备中 ，可以找到 networkInterface.getDisplayName() 名称
 *                       2.获取MTU 的大小
 *                       3.网卡子接口、虚拟接口、虚拟子接口
 *                       4.获取机器的硬件地址
 *                       5.获取ip 地址
 *                          1.获取ip 地址基本信息
 *                          2.获取本地主机和回环地址的基本信息
 *                          3.根据主机名称获取ip 地址
 *                          4.根据主机名称获取所有 ip 地址
 *                          5.根据ip 字节获取 InetAddress 信息
 *                          6.根据主机名和ip 字节获取 InetAddress 信息
 *                          7.获得全限主机名和主机名
 *                       6.InterfaceAddress 类的使用
 *                       7.判断是否为点对点的设备 以及 判断当前网络设备是否支持多播
 *                       8.NetworkInterface 类的静态方法
 *
 *
 *
 *
 *
 *;
 * @Author LP
 * @Date 2021/7/19
 * @Version 1.0
 **/
public class Network_01_NetworkInterface_Profile {


    /**
     * NetworkInterface 类的静态方法
     *      1.NetworkInterface.getByName("lo");
     *      2.
     *      3.
     *
     * @throws SocketException
     */
    @Test
    public void test_08() throws SocketException, UnknownHostException {
        NetworkInterface networkInterface = NetworkInterface.getByName("lo");//根据 networkinterface 的名称来获取 NetworkInterface 对象
        String name = networkInterface.getName();
        System.out.println(String.format("网络设备的名称：%s",name));

        NetworkInterface networkInterface1 = NetworkInterface.getByIndex(2);//根据索引获取 NetworkInterface 对象
        System.out.println(String.format("网络设备的名称：%s",networkInterface1.getName()));

        System.out.println();
        NetworkInterface networkInterface2 = NetworkInterface.getByInetAddress(InetAddress.getByName("127.0.0.1"));//根据ip 的InetAddress对象获取NetworkInterface对象
        System.out.println(networkInterface2.getName());
        System.out.println(networkInterface2.getDisplayName());



    }
    /**
     *
     * 判断当前网络设备是否为点对点的设备
     *
     * 点对点的设备是什么？通过拨号或者专线的方式建立点对点的连接已发送数据，使其成为各种主机、网桥或者路由器之间简单连接的一种通信解决方案；
     *
     * 什么是多播？先了解单播、广播，在看多播；
     *          单播：设备点对点方式的网络，访问网络，发邮件或者网络聊天，都是在使用点对点的方式 传输数据；
     *          广播：广播是一对多的形式，对网络中所有设备发送数据，不区分目标，容易造成“网络风暴” ，产生大量的网络垃圾数据，使网络变慢，严重时，造成网络瘫痪；
     *          再看多播：多播也成为组播，是一种一对多的网络。他可以对某些计算机的IP地址进行分组，进行发送数据的方式；组播这种方式比广播传输数据的效率更高，因为
     *                  目标明确；
     *                  -> 多播一般通过多播IP地址来实现，多播IP地址就是D类IP地址，即 224.0.0.0 ~ 239.255.255.255 之间的 IP 地址；
     *
     * @throws SocketException
     */
    @Test
    public void test_07() throws  SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            boolean pointToPoint = networkInterface.isPointToPoint();
            boolean supportsMulticast = networkInterface.supportsMulticast();

            System.out.println(String.format("getName 获得网络设备名称=%s", networkInterface.getName()));
            System.out.println(String.format("getDisplayName 获取网络设备的显示名称=%s", networkInterface.getDisplayName()));
            System.out.println(String.format("\t当前设备是否为点对点的设备：%s", pointToPoint));
            System.out.println(String.format("\t是否支持多播：%s", supportsMulticast));


            System.out.println();
            System.out.println();
        }
    }

    /**
     *
     * InterfaceAddress 类的使用
     *
     * InetAddress 对应的是ip地址信息的，而 InterfaceAddress 对应的是 网络接口的信息，可以在 InterfaceAddress 接口下获取 ip地址的 InetAddress 对象信息，
     *      以及多播地址的InetAddress 对象信息，还有子网掩码等；
     *
     * 什么是网络前缀长度 ：对于ipv4 来说，也被称为子网掩码，比如：8(255.0.0.0)、16(255.255.0.0)、24(255.255.255.0); 对于ipv6：128(::1/128) 等；
     *
     * 小结：每个 NetworkInterface 有多个 InterfaceAddress 对象，而对于每个 InterfaceAddress 对象，只有一个 InetAddress 对象
     * @throws SocketException
     */
    @Test
    public void test_06() throws  SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            System.out.println(String.format("getName 获得网络设备名称=%s", networkInterface.getName()));
            System.out.println(String.format("getDisplayName 获取网络设备的显示名称=%s", networkInterface.getDisplayName()));
            List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
            for (InterfaceAddress interfaceAddress : interfaceAddresses) {

                InetAddress address = interfaceAddress.getAddress();//返回 InterfaceAddress 的 InetAddress信息
                InetAddress broadcast = interfaceAddress.getBroadcast();//返回 InterfaceAddress 的广播地址的InetAddress，只有 ipv4 才有广播地址，ipv6没有广播地址；
                short networkPrefixLength = interfaceAddress.getNetworkPrefixLength();//返回 InterfaceAddress 前缀长度；
                if (address!=null) {
                    System.out.println(String.format("\taddress.getHostAddress()=%s",address.getHostAddress()));
                }
                if (broadcast!=null){
                    System.out.println(String.format("\tbroadcast.getHostAddress()=%s",broadcast.getHostAddress()));
                }
                System.out.println(String.format("\tinterfaceAddress.getNetworkPrefixLength()=%s",networkPrefixLength));
                System.out.println();
            }
        }
    }

    /**
     *
     * 获得全限主机名和主机名
     *
     * @throws UnknownHostException
     */
    @Test
    public void test_05_7() throws UnknownHostException {
        //String canonicalHostName = InetAddress.getLocalHost().getCanonicalHostName();
        //获取本地的主机信息
        InetAddress localHost = InetAddress.getLocalHost();
        String canonicalHostName = localHost.getCanonicalHostName();
        String hostAddress = localHost.getHostAddress();
        String hostName = localHost.getHostName();
        System.out.println(String.format("完全限定主机名称：%s", canonicalHostName));
        System.out.println(String.format("主机ip：%s", hostAddress));
        System.out.println(String.format("主机名称：%s", hostName));
        System.out.println();

        //使用域名创建 InetAddress 对象
        InetAddress byName = InetAddress.getByName("www.ibm.com");
        System.out.println(String.format("完全限定主机名称：%s",byName.getCanonicalHostName()));
        System.out.println(String.format("主机名称：%s", byName.getHostName()));
        System.out.println();

        //使用ip 创建 InetAddress 对象
//        InetAddress ipInetAddress = InetAddress.getByName("172.20.10.9");
        InetAddress ipInetAddress = InetAddress.getByName("192.168.32.1");
        System.out.println(String.format("完全限定主机名称：%s",ipInetAddress.getCanonicalHostName()));
        System.out.println(String.format("主机名称：%s", ipInetAddress.getHostName()));
        System.out.println();


    }
    /**
     *
     * 根据主机名和ip 字节获取 InetAddress 信息
     *
     * @throws UnknownHostException
     */
    @Test
    public void test_05_6() throws UnknownHostException {
        byte[] bytes={-64,-88,32,1};
        InetAddress inetAddress = InetAddress.getByAddress("aaaa", bytes);
        String hostAddress = inetAddress.getHostAddress();
        System.out.println(hostAddress);
        System.out.println(inetAddress.getHostName());
        System.out.println(inetAddress.getClass().getName());


    }

    /**
     * 根据ip 字节获取 InetAddress 信息
     * @throws UnknownHostException
     */
    @Test
    public void test_05_5() throws UnknownHostException {
        byte[] bytes={-64,-88,32,1};
        InetAddress inetAddress = InetAddress.getByAddress(bytes);
        System.out.println(inetAddress.getHostAddress());

    }
    /**
     *
     * 根据主机名称获取所有ip 地址
     *
     * @throws UnknownHostException
     */
    @Test
    public void test_05_4() throws UnknownHostException {
        InetAddress[] inetAddresses = InetAddress.getAllByName("PC-20140405GMFU");
        for (int i = 0; i < inetAddresses.length; i++) {
            InetAddress inetAddress = inetAddresses[i];
            byte[] address = inetAddress.getAddress();
            for (int j = 0; j < address.length; j++) {
                System.out.print((char)address[j]);
            }
            System.out.println();
            System.out.println(String.format("HostAddress=%s,class name=%s", inetAddress.getHostAddress(),inetAddress.getClass().getName()));
        }



        System.out.println("obj www.baidu.com below");
        InetAddress[] inetAddressesB = InetAddress.getAllByName("www.baidu.com");
        for (int i = 0; i < inetAddressesB.length; i++) {
            InetAddress inetAddress = inetAddressesB[i];
            System.out.println(String.format("HostAddress=%s,class name=%s", inetAddress.getHostAddress(),inetAddress.getClass().getName()));
        }



    }
    /**
     * 根据主机名称获取ip地址
     *       InetAddress.getByName(""); 参数可以是计算机名称、ip地址、也可以是域名；
     */
    @Test
    public void test_05_3() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("PC-20140405GMFU");//计算机名称
        System.out.println("PC-20140405GMFU's ip is:"+inetAddress.getHostAddress());


        InetAddress inetAddressBaidu = inetAddress.getByName("www.baidu.com");//域名
        System.out.println("www.baidu.com's ip is :"+inetAddressBaidu.getHostAddress());

    }

    /**
     * 获取本地主机和回环地址的基本信息
     *      获取本地主机:InetAddress.getLocalHost()，如果本地主机有多个ip地址，该方法只返回下标为0 的ip地址；
     *          如果想返回本机所有的ip地址，使用方法InetAddress.getAllByName;
     *      返回本机回环地址：InetAddress.getLoopbackAddress();
     * @throws SocketException
     */
    @Test
    public void test_05_2() throws  UnknownHostException {
        //单独通过 InetAddress 类获取ip信息
        InetAddress localHost = InetAddress.getLocalHost();
        InetAddress[] allByName = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());

        IPAddressUtil.convertFromIPv4MappedAddress(localHost.getAddress());
        String hostAddress = localHost.getHostAddress();
        System.out.println(hostAddress);
        System.out.println("localHost:");
        byte[] address = localHost.getAddress();
        for (int h = 0; h < address.length; h++) {
            System.out.print(address[h]+" ");
        }
        System.out.println();



        InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
        byte[] address1 = loopbackAddress.getAddress();
        System.out.println("loopbackAddress:");
        for (int i = 0; i < address1.length; i++) {
            System.out.print(address1[i]+" ");
        }
        System.out.println();


    }
    /**
     *
     * 获取ip 地址-基本信息
     *
     *      获取当前网络接口的网络列表:networkInterface.getInetAddresses()
     *      InetAddress 类可以表示互联网协议 ip 地址，通过该对象里面的部分方法获取 ip 地址信息，一个网络设备可以使用多个 ip 地址；
     *      InetAddress 有两个子类：Inet4Address Inet6Address，分别表示ipv4 的信息，ipv6 的信息
     *
     *
     *
     * @throws SocketException
     */
    @Test
    public void test_05_ipbasic() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();

            //排除不是 已经开启并正常工作的网络设备
            if (!networkInterface.isUp()) continue;
            //

            System.out.println(String.format("网络设备名称：%s" ,networkInterface.getName()));
            System.out.println(String.format("网络设备显示名称：%s",networkInterface.getDisplayName()));
            //获取当前网络接口的网络列表，此行代码就说明了一个网络设备可以使用多个 ip 地址；
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            System.out.println("网络设备接口的信息：");
            while (inetAddresses.hasMoreElements()){
                InetAddress inetAddress = inetAddresses.nextElement();
                String canonicalHostName = inetAddress.getCanonicalHostName();//获取网络的完全限定名称，完全限定名包括主机名称加上全路径，全路径为：序列中所有域成员；
                String hostName = inetAddress.getHostName();//主机名称
                byte[] address = inetAddress.getAddress();//获取主机ip地址的原始地址
                String hostAddress = inetAddress.getHostAddress();//获取主机ip地址
                System.out.println("\tcanonicalHostName="+canonicalHostName);
                System.out.println("\thostName="+hostName);
                System.out.println("\thostAddress="+hostAddress);
                System.out.println();

            }
            System.out.println();
        }

    }
    /**
     * 物理地址、mac地址、硬件地址表示的就是一个意思
     *
     * @throws SocketException
     */
    @Test
    public void test_04() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while(networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            boolean up = networkInterface.isUp();//表示是否已经开启并正常工作
            if (up){
                System.out.println(String.format("网络设备名称：%s", networkInterface.getName()));
                System.out.println(String.format("网络设备显示名称：%s", networkInterface.getDisplayName()));
                byte[] hardwareAddress = networkInterface.getHardwareAddress();

                if (!Objects.isNull(hardwareAddress))
                    System.out.println(String.format("物理地址：%s", MacAddressUtil.formatAddress(networkInterface.getHardwareAddress())));
                else
                    System.out.println(String.format("物理地址：%s", ""));
                System.out.println();
            }

        }


    }


    /**
     *
     * 获取子接口(windows系统不支持，linux支持)：什么是子接口，不添加新的物理网卡，基于原有的网络接口虚拟出来的一个网络设备进行通信，这个虚拟的网络接口可以理解为软件模拟的网卡，windows不支持子接口，linux支持；
     *
     * 虚拟子接口：通过方法 isVirtual()可以判断；来说下什么是虚拟子接口：在linux os 上，虚拟子接口作为物理接口的子接口被创建，并给于不同的设置(如ip地址、MTU 等),通常虚拟子接口的名称为 物理接口的名称+“:”+标识该子接口的编号，因为一个物理接口可能存在多个虚拟子接口；
     *          注意：虚拟接口 、虚拟子接口的区别，虚拟接口也是非硬件类的设备，也是由软件模拟的网络设备，该网络设备没有父网络接口；
     *          总结：虚拟接口：1.是软件模拟的网络设备，没有父网络接口 2.虚拟子接口：是软件模拟的网络设备，有父网络接口 3.虚拟接口不一定是虚拟子接口，但虚拟子接口一定是虚拟接口；
     *
     *
     * @throws SocketException
     */
    @Test
    public void test_03() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            int hashCode = networkInterface.hashCode();//接口的hashCode
            String displayName=networkInterface.getDisplayName();//网络设备的显示名称
            boolean isVirtual = networkInterface.isVirtual();//是否为虚拟子接口
            NetworkInterface parent = networkInterface.getParent();//父接口
            System.out.println(String.format("网络设备hashCode=%s", hashCode));
            System.out.println(String.format("网络设备的显示名称=%s", displayName));
            System.out.println(String.format("网络设备是否为虚拟网卡=%s",isVirtual));
            System.out.println(String.format("父接口=%s", parent));

            Enumeration<NetworkInterface> subInterfaces = networkInterface.getSubInterfaces();
            while (subInterfaces.hasMoreElements()){
                NetworkInterface subInterface = subInterfaces.nextElement();
                System.out.println(String.format("\tsub-网络设备名称=%s", subInterface.getName()));
                System.out.println(String.format("sub-网络设备的显示名称=%s", subInterface.getDisplayName()));
                System.out.println(String.format("是否为虚拟接口=%s", subInterface.isVirtual()));
                System.out.println(String.format("父接口的hashCode=%s", subInterface.getParent().hashCode()));



            }


        }

    }


    /**
     * MTU: 网络传输以数据包为基本单位，MTU 表示网络最大传输单元，单位是字节；
     *      以太网卡大多数默认MTU为1500字节，在ipv6 中，MTU 的范围是 1280-65535 ；
     *      MTU设置的大小与网络传输速度有关，如果设置过大，传输很快，因为发送的数据包少了，但延迟很大，因为对方需要一点一点的 处理数据，如果设置过小，传输速度慢，因为传输的数据包多了。所以，建议不要随意更改网卡的 MTU 的值，因为有可能会发生网络传输的故障，致使传输数据不完整，发生丢包的现象；
     *
     * @throws SocketException
     */
    @Test
    public void test_02() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            String name = networkInterface.getName();
            String displayName = networkInterface.getDisplayName();
            int mtu = networkInterface.getMTU();
            boolean up = networkInterface.isUp();
            if (up){
                System.out.println(String.format("正在启动并正常工作的网络设备显示名称=%s，网络设备的名称=%s，mtu=%s", displayName,name,mtu));
            }

        }

    }
    /**
     *
     * 1.获取网络接口的基本信息
     *
     * @throws SocketException
     */
    @Test
    public void test_01() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            String name = networkInterface.getName();//获取网络设备在os中的名称：该值不能得知具体设备的信息，只是一个代号，多数以eth开头，后面跟着序号，但序号不一定是连续的，比如eth0，eth1等等；
            String displayName = networkInterface.getDisplayName();//获取设备在os中的显示名称：该值包含厂商名称、网卡具体型号等信息；
            int index = networkInterface.getIndex();//获取网络接口的索引：此索引在不同的os中有可能不一样，值为大于等于0，未知的用-1表示；
            boolean up = networkInterface.isUp();//表示网络设备是否已经开启并正常工作；
            boolean loopback = networkInterface.isLoopback();//判断该网络接口是否是回环接口/ localhost 回调；如果是，工作状态是永远工作，且还是虚拟的，也就是说计算机上不存在这样的设备，那么存在的意义是什么呢 ？ 可以让计算机访问自己机器上的服务！

            if (up){


            System.out.println(String.format("getName 获得网络设备的名称=%s", name));
            System.out.println(String.format("displayName 获得网络设备的显示名称=%s", displayName));
            System.out.println(String.format("getIndex 获得网络设备的索引=%s",index));
            System.out.println(String.format("isUp 是否已经开启并运行=%s", up));
            System.out.println(String.format("isLoopback 是否是回环调用=%s", loopback));
            System.out.println();
            System.out.println();
            }
        }
    }
}

