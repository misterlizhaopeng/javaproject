import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        String inputfactory = in.nextLine();
//        String out = in.nextLine();
//        List<Integer> fac = new ArrayList<>();
//        //非 -
//
//        if (inputfactory.contains(",")) {
//            String[] s = inputfactory.split(",");
//            for (int i = 0; i < s.length; i++) {
//                if (s[i].contains("-")) {
//                    String ss = s[i];
//                    String[] split = ss.split("-");
//                    int f = Integer.valueOf(split[0]);
//                    int t = Integer.valueOf(split[1]);
//                    // 3-4 ;len 1;
//                    for (int j = f; j <= t; j++) {
//                        fac.add(j);
//                    }
//                } else {
//                    fac.add(Integer.valueOf(s[i]));
//                }
//            }
//        }
//        //vlan池搞定；
//
//        //解析资源池
//        fac.sort(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1 - o2;
//            }
//        });//sort ok


        BigDecimal big1 = new BigDecimal(10);

        BigDecimal big2 = new BigDecimal(11.79000000000000003);
        big1 = big1.add(big2).setScale(2,BigDecimal.ROUND_HALF_UP);
        //big1.compareTo(big2)
        System.out.println(big1);

//        big1=big1.subtract(new BigDecimal(1.21));
//
//        System.out.println(big1);
//        ///BigDecimal b3 = big1.add(big2);
//        System.out.println(big1.setScale(2,BigDecimal.ROUND_HALF_UP));


        System.out.println("sss");

    }
}

