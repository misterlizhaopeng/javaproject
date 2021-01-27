package com.lp.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * @ClassName com.lp.bio.CliSocket
 * @Deacription TODO
 * @Author LP
 * @Date 2020/12/17 13:01
 * @Version 1.0
 **/
public class CliSocket {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1", 8888);

        socket.getOutputStream().write("hello Ser".getBytes());
    }
}

