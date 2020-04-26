package _proxy._dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//动态代理的实现
public class Proxy_ {
    public static <T> T getInstance(T t) {
        // 其实分析动态代理可以分为两步：1.bind 把真是对象和代理对象进行绑定；2.invoke；调用真是对象的具体动作，都会执行invoke方法，实现具体的代理控制逻辑
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("proxy_.dynamci->具体方法执行前!");
                        Object result = method.invoke(t, args);
                        System.out.println("proxy_.dynamci->具体方法执行后!");
                        return result;
                    }
                });
    }
}
