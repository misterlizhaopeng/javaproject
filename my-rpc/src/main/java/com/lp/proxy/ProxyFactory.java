package com.lp.proxy;

import com.lp.netty_self.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName com.lp.proxy.ProxyObject
 * @Deacription TODO
 * @Author LP
 * @Date 2021/4/5 19:50
 * @Version 1.0
 **/
public class ProxyFactory<T> {
    public static <T> T getProxyObject(final Class interfaceName) {
        return (T) Proxy.newProxyInstance(interfaceName.getClassLoader(), new Class[]{interfaceName}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(interfaceName.getName(),method.getName(),method.getParameterTypes(),args);

                System.out.println("代理对象执行动作！");

                //方法的返回值
                return null;
            }
        });
    }
}

