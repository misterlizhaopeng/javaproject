package _proxy._dynamic;

public class WasProxyObjImpl2 implements  WasProxyObj2 {
    @Override
    public void action() {
        System.out.println("被代理的具体实现2");
    }

    @Override
    public void action2() {
        System.out.println("action2-被代理的具体实现2");
    }
}
