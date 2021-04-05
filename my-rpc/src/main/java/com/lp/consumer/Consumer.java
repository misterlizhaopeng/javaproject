package com.lp.consumer;

import com.lp.provider.service.HelloService;
import com.lp.proxy.ProxyFactory;

/**
 * @ClassName com.lp.consumer.Consumer
 * @Deacription TODO
 * @Author LP
 * @Date 2021/4/5 19:56
 * @Version 1.0
 **/
public class Consumer {
    public static void main(String[] args) {

        HelloService helloService = ProxyFactory.getProxyObject(HelloService.class);
        System.out.println(helloService.helloService("xxx"));
    }
}

