package nio_test.netty_.hpchat.lphandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.FileUpload;
import nio_test.netty_.hpchat.lpprotocol.FileUploadRequestPojo;
import nio_test.netty_.hpchat.util.MyConfig;
import nio_test.netty_.hpchat.util.ParamUtil;
import nio_test.netty_.hpchat.util.ResponseUtil;

import java.io.File;

public class LpFileUploadHandlerUseless extends SimpleChannelInboundHandler<FileUploadRequestPojo> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileUploadRequestPojo requestFile) throws Exception {
        FullHttpRequest request = requestFile.getRequest();
        FileUpload fileUpload = ParamUtil.getFileUpload(request);
        if (fileUpload == null || !fileUpload.isCompleted()) {
            ResponseUtil.sendHttpResponse(ctx, request, ResponseUtil.get400Response());
            return;
        }
        String fileName = fileUpload.getFilename();
        //毫秒数+.文件后缀
        String newName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        fileUpload.renameTo(new File(MyConfig.FILE_UPLOAD_ABSPATH_PREFIX + newName));
        ResponseUtil.sendHttpResponse(ctx, request, ResponseUtil.get200Response(MyConfig.FILE_UPLOAD_MAPPING_URL_PREFIX + newName));
    }
}
