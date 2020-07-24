package nio_test.netty_.hpchat.lpprotocol;

import io.netty.handler.codec.http.FullHttpRequest;

public class FileUploadRequestPojo {

    private FullHttpRequest request;

    public FileUploadRequestPojo(FullHttpRequest request) {
        this.request = request;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }

}