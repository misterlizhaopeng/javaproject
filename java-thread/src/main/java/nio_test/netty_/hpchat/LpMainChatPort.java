package nio_test.netty_.hpchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nio_test.netty_.hpchat.lpinit.LpChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * 程序主入口
 */
public class LpMainChatPort {
    private static final Logger logger = LoggerFactory.getLogger(LpMainChatPort.class);
    private int port;

    private void startHPServer() {
        //设计 一主多从 的服务架构
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)

                //服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
                // 【服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小】;
                //backlog对程序支持的连接数并无影响，backlog影响的是还没有被 accept 取出的连接
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new LpChannelInitializer());
        logger.info("高性能聊天室 server 启动了...");
        try {
            ChannelFuture cf = serverBootstrap.bind(port).sync();
            //等待通道关闭
            cf.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            logger.info("高性能聊天室 server 关闭了...");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8090;
        }
        new LpMainChatPort(port).startHPServer();


        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            logger.warn("jvm 即将关闭");
        }));

    }

    private LpMainChatPort(int port) {
        this.port = port;
    }
}
