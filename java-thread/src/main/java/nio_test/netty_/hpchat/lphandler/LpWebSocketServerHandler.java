package nio_test.netty_.hpchat.lphandler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import nio_test.netty_.hpchat.pojo.MsgTag;

import javax.sql.rowset.BaseRowSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class LpWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //管道池
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM");
    private static final ConcurrentHashMap<MsgTag, Channel> users = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Channel, MsgTag> users_ = new ConcurrentHashMap<>();
    //计数
    private static final LongAdder longAdder = new LongAdder();

    /**
     * 表示客户端发过来数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel inch = ctx.channel();
        //处理协议-start
        JSONObject json = JSONObject.parseObject(msg.text());
        Integer tk = json.getInteger("tk");
        String nick = json.getString("nick");
        String chatMessage = json.getString("chatMessage");

        MsgTag msgTag = new MsgTag();
        msgTag.setTk(tk);
        msgTag.setNick(nick);
        msgTag.setChatMessage(chatMessage);
        MsgTag backClimsg = null;
        String backMsg = null;
        switch (tk) {
            //认证、注册
            case 1:

                ConcurrentHashMap.KeySetView<MsgTag, Channel> onceTagkey = users.keySet();
                Iterator<MsgTag> it = onceTagkey.iterator();
                while (it.hasNext()) {
                    MsgTag next = it.next();
                    if (next.getNick().equals(nick)) {
                        MsgTag onceTagObj = new MsgTag();
                        onceTagObj = new MsgTag();
                        onceTagObj.setNick(null);
                        onceTagObj.setTk(15);
                        onceTagObj.setChatMessage("存在重复昵称");
                        String onceNick = JSONObject.toJSONString(onceTagObj);
                        inch.writeAndFlush(new TextWebSocketFrame(onceNick));
                        //存在相同昵称，关闭当前管道连接
                        //inch.close();
                        return;
                    }
                }


                longAdder.increment();
                users.put(msgTag, ctx.channel());
                users_.put(ctx.channel(), msgTag);
                backClimsg = new MsgTag();
                backClimsg.setNick(null);
                backClimsg.setTk(11);
                backClimsg.setChatMessage("认证成功");
                backMsg = JSONObject.toJSONString(backClimsg);
                inch.writeAndFlush(new TextWebSocketFrame(backMsg));
                break;
            case 2:
                //接受客户端发送的消息 - 群发
                backClimsg = new MsgTag();
                backClimsg.setNick(nick);
                backClimsg.setTk(12);
                backClimsg.setChatMessage(chatMessage);
                backMsg = JSONObject.toJSONString(backClimsg);
                for (Channel ch : channels) {
                    if (ch != inch) {
                        //非阻塞的；给别的channel发送
                        ch.writeAndFlush(new TextWebSocketFrame(backMsg));
                    }
                }
                break;
            case 3:
                //查询所有登录人的信息
                ConcurrentHashMap.KeySetView<MsgTag, Channel> msgTags = users.keySet();
                StringBuilder builder = new StringBuilder();
                msgTags.forEach(a -> {
                    builder.append(a.getNick() + ",");
                });
                backClimsg = new MsgTag();
                //群发
                backClimsg.setNick(null);
                backClimsg.setCounts(longAdder.sum());
                backClimsg.setTk(13);
                backClimsg.setChatMessage(builder.toString());
                backMsg = JSONObject.toJSONString(backClimsg);
                //谁请求，发给谁
                inch.writeAndFlush(backMsg);
                break;
            case 4:
                //给指定的人发消息 - 个发
                String toNick = json.getString("toNick");
                ConcurrentHashMap.KeySetView<MsgTag, Channel> msgTags1 = users.keySet();
                Iterator<MsgTag> iterator = msgTags1.iterator();
                while (iterator.hasNext()) {
                    MsgTag next = iterator.next();
                    if (next.getNick().equals(toNick)) {
                        Channel channel = users.get(next);
                        MsgTag toBackClimsg = new MsgTag();
                        toBackClimsg.setNick(null);
                        toBackClimsg.setCounts(longAdder.sum());
                        toBackClimsg.setTk(14);
                        toBackClimsg.setChatMessage(chatMessage);
                        String toBackMsg = JSONObject.toJSONString(toBackClimsg);
                        channel.writeAndFlush(toBackMsg);
                        return;
                    }
                }
                break;
        }
        //处理协议-end
    }

    /**
     * 有客户端加入,handlerAdded 比 channelActive 执行要早（俗称【先加入然后在线】）；
     * channelInactive 比 handlerRemoved 执行要早（俗称【先掉线然后离开】）
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //String curUser = "";
        Channel inch = ctx.channel();
        // Broadcast a message to multiple Channels
        //[main]channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + inch.remoteAddress() + " 加入"));
        channels.add(inch);
        //当前人的通道
        //users.put(curUser, inch);
        System.out.println("Client:" + inch.remoteAddress() + "加入");
    }

    /**
     * 有客户端离开
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel inch = ctx.channel();
        // Broadcast a message to multiple Channels
        //[main] channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + inch.remoteAddress() + " 离开"));
        channels.remove(inch);
        MsgTag msgTag = users_.get(inch);
        users.remove(msgTag,inch);

        System.out.println("Client:" + inch.remoteAddress() + "离开");
    }

    /**
     * 会话建立
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel inch = ctx.channel();
        System.out.println("Client:" + inch.remoteAddress() + "在线");
    }

    /**
     * 会话结束
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel inch = ctx.channel();
        System.out.println("Client:" + inch.remoteAddress() + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel inch = ctx.channel();
        System.err.println("Client:" + inch.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
