package del;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

public class Bad {
    int a;

    public Bad() {
    }

    public Bad(int a) {
        this.a = a;
    }

    public static Stack s = new Stack();

    static {
        s.push(new Object());
        s.pop(); //这里一个对象发送内存泄漏
        s.push(new Object()); // 上面的对象可以被回收了，等于是自愈了
    }

    public static void main(String[] args) {
        System.out.println("---------->");
    }

    @Test
    public void test1() throws IOException {
    }
}