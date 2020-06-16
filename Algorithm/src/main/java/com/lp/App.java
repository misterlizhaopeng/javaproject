package com.lp;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Integer[] arrInt = {1, 11, 232, 7, 3, 2, 4, 33, 43, 2, 21, 9};
        Integer[] arrInt2 = {2, 7, 6, 3};
        //maopao(arrInt);
        quickSort(arrInt2, 0, arrInt2.length - 1);

        List<Integer> ints = Arrays.asList(arrInt2);
        ints.forEach(a -> {
            System.out.println(a);
        });

    }


    /**
     * 冒泡排序
     *
     * @param arr
     */
    public static void maopao(Integer[] arr) {
        // int[] arrInt = {1, 11, 232, 7, 3};
        int tem = 0;
        for (int i = 0, len = arr.length - 1; i < len; i++) {
            for (int j = 0, jlen = arr.length - 1 - i; j < jlen; j++) {
                if (arr[j] < arr[j + 1]) {
                    tem = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tem;
                }
            }
        }
    }

    /**
     * 快速排序 时间复杂度 ，O(N)/O(logn)
     *
     * @param arr
     * @param _low
     * @param _high
     */
    public static void quickSort(Integer[] arr, int _low, int _high) {
        //当前一次计算 start
        int low = _low;
        int high = _high;

        if (low >= high)
            return;
        int temp = arr[low];

        while (high != low) {


            while (arr[high] >= temp && high > low)
                high--;
            arr[low] = arr[high];

            while (arr[low] <= temp && high > low)
                low++;
            arr[high] = arr[low];

        }

        arr[high] = temp;
        //当前一次计算 end

        quickSort(arr, _low, low - 1);  //在计算从最左侧到基于上面基准量-1
        quickSort(arr, high + 1, _high);  //在基于上面基准量+1，在计算(在计算和第一个递归意思一样)，这是个人总结
    }

}
