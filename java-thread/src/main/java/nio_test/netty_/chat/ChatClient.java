package nio_test.netty_.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());

                            pipeline.addLast(new ChatClientHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 9200).sync();
            // 获取channel
            Channel channel = cf.channel();
            System.out.println("========" + channel.localAddress() + "========");
            //客户端需要输入信息， 创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                //通过 channel 发送到服务器端
                channel.writeAndFlush(msg);
            }

//            for (int i = 0; i < 200; i++) {
//                channel.writeAndFlush("hello，zhangsan");
//            }
//            Thread.sleep(30000);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
