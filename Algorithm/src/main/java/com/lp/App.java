package com.lp;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        int[] arrInt = {1, 11, 232, 7, 3, 2, 4, 33, 43, 2, 21, 9};
        Integer[] arrInt2 = {2, 7, 6, 3};
        //maopao(arrInt);
        //quickSort(arrInt2, 0, arrInt2.length - 1);
        int[] nums = sortMerge(arrInt, 0, arrInt.length - 1);

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


    /**
     * 归并排序
     *
     * @return
     */
    public static int[] sortMerge(int[] nums, int l, int r) {
        //切到左索引等于右索引的时候，返回当前值，否则，递归切分，直至切到单个元素；（递归出口）
        if (l == r) return new int[]{nums[l]};
        int mid = l + (r - l) / 2;//l 从0开始，r 为当前数组的长度，这里
        int[] leftArr = sortMerge(nums, l, mid);//左序列数组
        int[] rightArr = sortMerge(nums, mid + 1, r);//右序列数组
        int[] newArr = new int[leftArr.length + rightArr.length];//当前转移到的目标数组

        //开始比较两个序列（左右序列）
        int newIndex = 0, left = 0, right = 0;
        while (left < leftArr.length && right < rightArr.length) {
            newArr[newIndex++] = leftArr[left] < rightArr[right] ? leftArr[left++] : rightArr[right++];
        }
        while (left < leftArr.length)
            newArr[newIndex++] = leftArr[left++];
        while (right < rightArr.length)
            newArr[newIndex++] = rightArr[right++];
        return newArr;
    }

}
