package com.lp.netty_self;

import com.lp.tools.LocalRegister;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

public class RequestHandler implements ChannelHandler {

    @Override
    public void handler(ChannelHandlerContext ctx, Invocation invocation) throws Exception{

        Class serviceImpl = LocalRegister.get(invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamTypes());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());

        // 返回服务结果
        ctx.writeAndFlush("Netty:" + result);
    }
}
