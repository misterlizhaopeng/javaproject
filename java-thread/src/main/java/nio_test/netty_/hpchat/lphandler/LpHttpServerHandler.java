package nio_test.netty_.hpchat.lphandler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import nio_test.netty_.hpchat.lpprotocol.FileUploadRequestPojo;
import nio_test.netty_.hpchat.util.MyConfig;
import nio_test.netty_.hpchat.util.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

public class LpHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(LpHttpServerHandler.class);
    private String url;


    public LpHttpServerHandler() {
    }

    public LpHttpServerHandler(String url) {
        this.url = url;
    }

    static {
    }

    // var s=new WebSocket('ws://localhost:8090/ws') 执行当前代码，执行
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (url.equalsIgnoreCase(request.getUri())) {
            ctx.fireChannelRead(request.retain());//如果是/ws请求，直接触发下一个 channelHandler 处理数据；
        } else if (isFileUpload(request)) {
            ctx.fireChannelRead(new FileUploadRequestPojo(request));
        } else {
            //非 "/ws" 请求
            String uri = request.uri();
            if ("/favicon.ico".equals(uri)) return;
            URL location = LpHttpServerHandler.class.getProtectionDomain().getCodeSource().getLocation();
            if (HttpHeaders.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
            File _file;
            String _uri = null;
            if (uri.equals("/")) {
                try {
                    //String path = _uri = location.toURI() + "chat.html";
                    String path = _uri = location.toURI() + "default.html";//"chat_1.html index";
                    path = !path.contains("file:") ? path : path.substring(5);
                    _file = new File(path);
                } catch (URISyntaxException e) {
                    throw new IllegalStateException("Unable to locate WebsocketChatClient.html", e);
                }
            } else {
                String mainDir = (_uri = location.toURI() + "").substring(6);
                String filename = uri.substring(uri.indexOf("/") + 1);
                //真实的文件路径
                String ft = mainDir + filename;
                _file = new File(ft);
                if (!_file.exists()) {
                    //请求的文件不存在
                    String _errPath = _uri + "error.html";
                    _errPath = !_errPath.contains("file:") ? _errPath : _errPath.substring(5);
                    _file = new File(_errPath);
                    logger.error("请求的文件不存在");
                }
            }
            RandomAccessFile file = null;
            HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
            boolean keepAlive = HttpHeaders.isKeepAlive(request);
            try {

                file = new RandomAccessFile(_file, "r");
                if (keepAlive) {
//                    response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
//                    response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                    response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                    response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                }
                ctx.write(response);
                if (ctx.pipeline().get(SslHandler.class) == null) {
                    ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
                } else {
                    ctx.write(new ChunkedNioFile(file.getChannel()));
                }
                ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                if (!keepAlive) {
                    future.addListener(ChannelFutureListener.CLOSE);
                }
            } catch (Exception ex) {
                ctx.writeAndFlush(ex.toString());
            }
            file.close();
        }
    }

    private RandomAccessFile getFile(String name) {
        return null;
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    //判断是否为文件上传
    private boolean isFileUpload(FullHttpRequest request) {
        //1、判断是否为文件上传自定义URI 3、判断是否为POST方法 2、判断Content-Type头是否包含multipart/form-data
        String uri = ParamUtil.getUri(request);
        String contentType = request.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (contentType == null || contentType.isEmpty()) {
            return false;
        }
        return MyConfig.FILE_UPLOAD_URL.equals(uri)
                && request.method() == HttpMethod.POST
                && contentType.toLowerCase().contains(HttpHeaderValues.MULTIPART_FORM_DATA);
    }

    //判断是否为websocket握手请求
    private boolean isWebSocketHandShake(FullHttpRequest request){
        //1、判断是否为get 2、判断Upgrade头是否包含websocket 3、Connection头是否包含upgrade
        return request.method().equals(HttpMethod.GET)
                && request.headers().contains(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET, true)
                && request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, true);
    }
}
