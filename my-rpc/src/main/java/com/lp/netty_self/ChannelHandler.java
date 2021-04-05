package com.lp.netty_self;

import io.netty.channel.ChannelHandlerContext;

public interface ChannelHandler {

    public void handler(ChannelHandlerContext ctx, Invocation invocation) throws Exception;

}
