package nio_test.netty_.hpchat.lphandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import nio_test.netty_.hpchat.pojo.MsgTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;


public class LpBinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(LpBinaryWebSocketFrameHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        ByteBuf byteBuf = Unpooled.directBuffer(msg.content().capacity());
        byteBuf.writeBytes(msg.content());
        LpWebSocketServerHandler.channels.writeAndFlush(new BinaryWebSocketFrame(byteBuf));


       /* Channel inch = ctx.channel();
        ChannelGroup channels = LpWebSocketServerHandler.channels;
        for (Channel ch : channels) {
            ConcurrentHashMap<Channel, MsgTag> user = LpWebSocketServerHandler.users_;
            MsgTag msgTag = user.get(ch);
            String nick = msgTag.getNick();
            //给所有的人发送图片，如何把nick放到前端的msg中，并且在前端解析出来 ？当前问题暂时保留，没时间弄了，太费时间了；2020年7月24日23:22:11
            //非阻塞的；给别的channel发送
            if (ch != inch) {
                ch.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
            } else {
                ch.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
            }
        }*/


    }
}  