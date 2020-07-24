package nio_test.netty_.hpchat.lpinit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import nio_test.netty_.hpchat.lphandler.LpBinaryWebSocketFrameHandler;
import nio_test.netty_.hpchat.lphandler.LpHttpServerHandler;
import nio_test.netty_.hpchat.lphandler.LpWebSocketServerHandler;

public class LpChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("http-decoder", new HttpRequestDecoder())
        //ch.pipeline().addFirst(new HttpServerCodec());
        //请求头、请求体的数据包合在一起，65535为数据最大值；
        .addLast("http-aggregator", new HttpObjectAggregator(65536))
        // webSocket 数据压缩扩展，当添加这个的时候 WebSocketServerProtocolHandler 的第三个参数需要设置成true
        .addLast(new WebSocketServerCompressionHandler())
        .addLast("http-encoder", new HttpResponseEncoder())
        //支持大文件传输
        .addLast("http-chunked", new ChunkedWriteHandler())
        .addLast("http-server", new LpHttpServerHandler("/ws"))
                // 聚合 websocket 的数据帧，因为客户端可能分段向服务器端发送数据
        .addLast(new WebSocketFrameAggregator(10 * 1024 * 1024))
        // 实现websocket协议的[编码和解码]，监视/ws请求，被认为是 websocket 请求
        .addLast("WebSocket-protocol", new WebSocketServerProtocolHandler("/ws",null,true,10485760))

        // 业务数据的发送，真正的业务处理
        .addLast("WebSocket-handler", new LpWebSocketServerHandler())
        .addLast("fileUploadHandler", new LpBinaryWebSocketFrameHandler());
        //ch.pipeline().addLast("fileUploadHandler", new LpFileUploadHandler());
    }
}
