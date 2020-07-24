package nio_test.netty_.hpchat.util;

public class MyConfig {

    //websocket的自定义id
    public static final String SOCKET_ID = "userId";
    //controller的路径
    public static final String CONTROLLER_PATH = "com.wode.http.controller";
    
    //文件上传请求uri
    public static final String FILE_UPLOAD_URL = "/upload";
    //外部访问本地文件映射路径
    public static final String FILE_UPLOAD_MAPPING_URL_PREFIX = "http://localhost:8090/file/";
    //文件上传本地绝对路径前缀
    public static final String FILE_UPLOAD_ABSPATH_PREFIX = "E:/tmp/";
}