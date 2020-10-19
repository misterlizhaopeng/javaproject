package del_test;

public class TstAdd {
    public static void main(String[] args) {

        int j = 1;
        j++;//2
        System.out.println(j);
        int k = j++;// k=2
        int m = ++j;// m=4
        System.out.println(k * m);// 3 * 4 = 12

        int i = 1;
        int ii = i++;// ii=1
        int iii = ++i;//iii=3
        System.out.println(i++ * ++i);// 1*2  3*5=15
        System.out.println(++i * i++);//6*6=36
    }
}


class T1 {


    public void t1() {
        try {
            throw new Exception();
        } catch (Exception ex) {
            //throw new Throwable();
        }
//        finally {
//            throw new Throwable();
//        }
    }

}

class T2 {
    public T2() {
    }
}

class T3 extends T2 {
    public T3() {
        super();
        System.out.println(this);
    }
}