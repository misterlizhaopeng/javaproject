package _proxy._static;

public class MT {
    public static void main(String[] args) {

        Proxy proxy =new Proxy(new WasProxyObjImpl());
        proxy.action();
    }
}
