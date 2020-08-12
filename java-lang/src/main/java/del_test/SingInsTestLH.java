package del_test;

public class SingInsTestLH {
    private SingInsTestLH() {}

    private volatile static SingInsTestLH singInsTestLH = null;

    public static SingInsTestLH getInstance() {
        if (singInsTestLH == null) {
            synchronized (SingInsTestLH.class) {
                if (singInsTestLH != null) {
                    singInsTestLH = new SingInsTestLH();
                }
            }
        }
        return singInsTestLH;
    }
}
