package com.lp.provider.impl;

import com.lp.provider.service.HelloService;

/**
 * @ClassName com.lp.impl.HelloServiceImpl
 * @Deacription TODO
 * @Author LP
 * @Date 2021/4/5 18:56
 * @Version 1.0
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String helloService(String input) {
        return "服务：返回的结果为：" + input;
    }
}

