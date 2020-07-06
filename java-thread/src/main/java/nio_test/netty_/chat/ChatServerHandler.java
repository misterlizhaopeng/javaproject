package nio_test.netty_.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

//当前类比较ChannelInboundHandlerAdapter，简化了接受的数据为String；
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //GlobalEventExecutor.INSTANCE是全局的事件执行器，是一个单例
    private static final DefaultChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final ConcurrentHashMap<String, ChannelHandlerContext> concurrentHashMap = new ConcurrentHashMap<>();

    //表示 channel 处于就绪状态, 提示上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其它在线的客户端
        //该方法会将 channelGroup 中所有的 channel 遍历，并发送消息
        channels.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + "上线了" + simpleDateFormat.format(new Date()));
        channels.add(channel);
        System.out.println(ctx.channel().remoteAddress() + " 上线了" + "\n");
    }

    //表示 channel 处于不活动状态, 提示离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + " 下线了" + "\n");
        System.out.println(ctx.channel().remoteAddress() + " 下线了" + "\n");
        System.out.println("channelGroup size=" + channels.size());
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取当前channel
        Channel channel = ctx.channel();

        // [点对点]-start
        //tk#objectNumber#msg
        //tk:0表示认证（认证的时候把当前的channel添加到缓存中）；1表示发送
        // 测试数据：
        //0#1#添加当前执行者，进行认证
        //1#3#给1发送消息
        //0#2#添加当前执行者，进行认证
        String[] split = msg.split("#");
        if (split.length == 3) {
            String tk = split[0];
            String objectNumber = split[1];
            String tmpMsg = split[2];
            if (tk.equals("0")){
                concurrentHashMap.put(objectNumber, ctx);
                System.out.println("server:人员"+objectNumber+"已上线！");
                channel.writeAndFlush("[ 自己 ]发送了消息：" + tmpMsg + "\n");
            }
            else if (tk.equals("1")) {
                if (concurrentHashMap.containsKey(objectNumber)) {
                    ChannelHandlerContext tmpChannel = concurrentHashMap.get(objectNumber);
                    tmpChannel.writeAndFlush(tmpMsg);
                }else {
                    channel.writeAndFlush("没有你要找的人，请保证聊天对象上线！");
                }
            }
            return;
        }
        // [点对点]-end


        //这时我们遍历 channelGroup, 根据不同的情况， 回送不同的消息
        channels.forEach(ch ->

        {
            //不是当前的 channel,转发消息
            if (channel != ch) {
                ch.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + " 发送了消息：" + msg + "\n");
            } else {
                ch.writeAndFlush("[ 自己 ]发送了消息：" + msg + "\n");
            }
        });
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
