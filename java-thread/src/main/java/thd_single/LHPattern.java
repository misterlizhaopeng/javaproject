package thd_single;

public class LHPattern {
    private static LHPattern lhPattern = null;

    private LHPattern() {
    }

    public static LHPattern getLHPatternIns() {
        if (lhPattern == null) {//2.基于下面线程访问锁耗费资源，加了一层判断
            synchronized (LHPattern.class) {//1.每个线程到来都需要判断一下锁，比较耗费资源
                if (lhPattern == null) {
                    lhPattern = new LHPattern();
                }
            }
        }
        return lhPattern;
    }
}
