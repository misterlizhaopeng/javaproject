package com.lp.netty_self;

import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName com.lp.netty_self.Task
 * @Deacription TODO
 * @Author LP
 * @Date 2021/4/5 19:27
 * @Version 1.0
 **/
public class Task implements Runnable {
    private Invocation invocation;
    private ChannelHandler channelHandler;
    private ChannelHandlerContext channelHandlerContext;

    public Task(Invocation invocation, ChannelHandler channelHandler, ChannelHandlerContext channelHandlerContext) {
        this.invocation = invocation;
        this.channelHandler = channelHandler;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void run() {
        try {
            channelHandler.handler(channelHandlerContext,invocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

