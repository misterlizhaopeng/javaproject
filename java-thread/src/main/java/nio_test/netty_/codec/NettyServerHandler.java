package nio_test.netty_.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MsgEachOther){
            MsgEachOther msgo= (MsgEachOther)msg;
            MsgContent<String> content = msgo.getContent();
            MsgContent<User> contentU = msgo.getContent();
        }
        //System.out.println("从客户端读取到String：" + msg.toString());
        if (msg instanceof C){
            System.out.println("从客户端读取到Object-C：" + ((C)msg).toString());
        }
        if (msg instanceof User){
            System.out.println("从客户端读取到Object-User：" + ((User)msg).toString());
        }
        //System.out.println("从客户端读取到Long：" + (Long)msg);
        //给客户端发回一个long数据
        ctx.writeAndFlush(2000L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
