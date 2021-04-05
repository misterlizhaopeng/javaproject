package com.lp.tools;

/**
 * @ClassName com.lp.tools.URL
 * @Deacription 服务地址（ip+port）
 * @Author LP
 * @Date 2021/4/5 19:06
 * @Version 1.0
 **/
public class URL {
    private String hostname;
    private int port;

    public URL(String hostname,int port){
        this.hostname = hostname;
        this.port = port;
    }
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "URL{" +
                "hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }
}

