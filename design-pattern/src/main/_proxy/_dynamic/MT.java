package _proxy._dynamic;
public class MT {

    public static void main(String[] args) {

        //dialing一个接口
        WasProxyObj wasProxyObj = Proxy_.getInstance(new WasProxyObjImpl());
        wasProxyObj.action();

        WasProxyObj2 wasProxyObj2 = Proxy_.getInstance(new WasProxyObjImpl2());
        wasProxyObj2.action();
        wasProxyObj2.action2();



}
}
