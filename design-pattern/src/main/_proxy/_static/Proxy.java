package _proxy._static;

//代理对象
public class Proxy {
    private  WasProxyObj wasProxyObj;
    public Proxy(WasProxyObj wasProxyObj){
        this.wasProxyObj=wasProxyObj;
    }

    //代理去执行被代理对象的方法
    public void action(){
        wasProxyObj.action();
    }
}
