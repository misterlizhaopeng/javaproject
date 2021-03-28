package test;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName fs.MapperProxy
 * @Deacription 接口对应的代理对象，接口类型为T
 * @Author LP
 * @Date 2021/3/19 22:21
 * @Version 1.0
 **/
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private final Class<T> mapperInterface;

    public MapperProxy(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象");
        return method.invoke(this, args);
    }
}

