package nio_test.netty_.splitpacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyClientMessageEncoder extends MessageToByteEncoder<MyMessageProtocol> {
    private int cou;

    @Override
    protected void encode(ChannelHandlerContext ctx, MyMessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyMessageEncoder encode 方法被调用，第 " + (++cou) + " 次");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }

}
