package thd_single;

//饿汉式-单例模式
public class EHParttern {
    private final static EHParttern EH_PARTTERN = new EHParttern();
    private EHParttern() {
    }
    public static EHParttern getEhParttern() {
        return EH_PARTTERN;
    }
}
