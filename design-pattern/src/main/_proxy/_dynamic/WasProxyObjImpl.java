package _proxy._dynamic;

import _proxy._dynamic.WasProxyObj;

public class WasProxyObjImpl implements  WasProxyObj {
    @Override
    public void action() {
        System.out.println("被代理的具体实现");
    }
}
