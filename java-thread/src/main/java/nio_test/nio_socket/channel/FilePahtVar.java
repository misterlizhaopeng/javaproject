package nio_test.nio_socket.channel;

/**
 * @ClassName nio_test.nio_socket.channel.FilePahtVar
 * @Deacription :
 * @Author LP
 * @Date 2021/7/6
 * @Version 1.0
 **/
public class FilePahtVar {
    private static final String PATH_FRM_DISK="f:\\del\\testFile\\";
    /**
     * 文件路径
     *
     * @param fileName
     * @return
     */
    public static String getFile(String fileName) {
        String str = PATH_FRM_DISK ;
        str += fileName;
        return str;
    }
}

