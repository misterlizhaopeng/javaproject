package com.lp.provider;

import com.lp.netty_self.NettyServer;
import com.lp.tools.LocalRegister;
import com.lp.tools.URL;
import com.lp.provider.service.HelloService;
import com.lp.provider.impl.HelloServiceImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName com.lp.ActionStarter
 * @Deacription TODO
 * @Author LP
 * @Date 2021/4/5 18:57
 * @Version 1.0
 **/
public class ActionStarter {
    public static void main(String[] args) throws IOException {
        //服务的名称
        String serviceName = HelloService.class.getName();

        //当前服务实例对象
        URL url = new URL(InetAddress.getLocalHost().getHostName(), 8999);

        //把服务（接口 + 实现）注册到本地注册中心
        LocalRegister.register(serviceName, HelloServiceImpl.class);

        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostname(), url.getPort());
        System.out.println(String.format("success,成功暴露%s服务,地址为%s", serviceName,url.toString()));

        System.in.read();
    }
}

