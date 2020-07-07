package nio_test.netty_.base;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

public class A {
    public static void main(String[] args) {
        System.out.println(SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));;
    }
}
