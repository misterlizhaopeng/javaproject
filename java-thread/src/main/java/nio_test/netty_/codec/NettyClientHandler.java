package nio_test.netty_.codec;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到服务器消息:" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler发送数据");
        //ctx.writeAndFlush("测试String编解码");
        //测试对象编解码
        ctx.writeAndFlush(new User(1,"zhangsan-lp"));
        ctx.writeAndFlush(new C(10, "c-obj"));
        for (int i = 0; i < 10; i++) {
            ctx.channel().writeAndFlush(new C(10, "c-obj"));
        }
        //测试自定义Long数据编解码器
        //ctx.writeAndFlush(1000L);

    }
}
