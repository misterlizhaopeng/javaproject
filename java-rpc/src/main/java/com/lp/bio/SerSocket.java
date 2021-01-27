package com.lp.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @ClassName com.lp.bio.SerSocket
 * @Deacription TODO
 * @Author LP
 * @Date 2020/12/17 12:52
 * @Version 1.0
 **/
public class SerSocket {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            Socket acceptSocket = serverSocket.accept();

            System.out.println("已连接");
            new Thread(() -> {
                try {
                    executer(acceptSocket);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();

        }
    }

    public static void executer(Socket socket) throws IOException {
        System.out.println("一个线程被启动...");
        byte[] localByte = new byte[1024];
        int readLen = socket.getInputStream().read(localByte);
        System.out.println(new String(localByte,0,readLen));

    }
}

