package jt_7_7_8;

// 空白final ->结论：必须在域的定义处 或者 在[每个] 构造函数里初始化
public class BlankFinal {
    private final int i = 0;//初始化i
    private final int j;//blank final
    private final Poppt p;//blank final reference

    // 空白 final 必须在构造函数里面初始化
    public BlankFinal() {
        j = 1;
        p = new Poppt(1);
    }
    int a =34;
    public BlankFinal(int x){
        j=x;
        p=new Poppt(x);
        a=78;
    }

    public static void main(String[] args) {
        new BlankFinal();
        new BlankFinal(2);
    }
}

class Poppt {
    private int i;

    Poppt(int ii) {
        i = ii;
    }
}