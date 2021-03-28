package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName fs.ObjectInterfaceProxy
 * @Deacription TODO
 * @Author LP
 * @Date 2021/3/19 21:59
 * @Version 1.0
 **/
public class ObjectInterfaceProxy {

    public static <T> T getProxyObj(T t) {
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(t, args);
                    }
                });

    }

    public static <T> T getProxyObj1(T t) {
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(),
                (p, m, a) -> m.invoke(t, a)
        );
    }
}

