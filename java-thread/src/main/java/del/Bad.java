package del;

public class Bad {
    public static Stack s = new Stack();

    static {
        s.push(new Object());
        s.pop(); //这里一个对象发送内存泄漏
        s.push(new Object()); // 上面的对象可以被回收了，等于是自愈了
    }
}