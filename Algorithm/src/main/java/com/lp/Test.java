package com.lp;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @ClassName com.lp.Test
 * @Deacription TODO
 * @Author LP
 * @Date 2020/10/22 0:46
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        System.out.println("abcxxx");
        Integer[] redPackage = getRedPackage(0.2d, 6);
        Double _amount = 0d;
        List<Integer> redPackages = Arrays.asList(redPackage);
        for (Integer unit : redPackages) {
            _amount += unit;
            Float price = Float.valueOf(unit / 100f);
            System.out.println("单价为：" + price);
            System.out.println(unit);
        }

        System.out.println("总计：" + _amount);

    }




    /**
     * 拆红包
     *
     * @param amount 总金额
     * @param count  红包个数
     * @return
     */
    public static Integer[] getRedPackage(Double amount, Integer count) {
        Integer[] resultRedPackage = new Integer[count];
        //分开
        int fen = (int) (amount * 100);
        if (fen < count || fen < 1) {
            throw new IllegalArgumentException("被拆分的总金额不能小于1分");
        }
        // 创建一个长度等于n的红包数组
        //第一步 每个红包先塞1分
        Arrays.fill(resultRedPackage, 1);
        //总金额减去已分配的n 分钱
        fen -= count;
        //第二步，循环遍历如果剩余金额>0 则一直分配
        int i = 0;  //从第一个红包进行分配

        //创建一个随机分配对象
        Random random = new Random();
        while (fen > 1) {
            int f = random.nextInt(fen);  //创建范围[0,fen)
            resultRedPackage[i++ % count] += f;
            fen -= f;
        }
        //判断剩余未分配的金额是否大于0,如果大于0，可以把剩下未分配金额塞到第一个红包中
        if (fen > 0) {
            resultRedPackage[0] += fen;
        }
        //end
        return resultRedPackage;
    }
}

