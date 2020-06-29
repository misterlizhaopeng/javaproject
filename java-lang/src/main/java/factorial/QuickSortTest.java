package factorial;

import java.util.Arrays;
import java.util.List;

public class QuickSortTest {


    public static void main(String[] args) {
        int[] arr = {-1, 0, 1, 2, 5, 8, 22, 22, 23, 38, 49, 65, 76, 97};

        int i = searchSplit(arr, 2);
        System.out.println("索引的位置为："+i);
    }

    public static int searchSplit(int[] arr, int item) {
        int low = 0, hight = arr.length - 1;

        while (low <= hight) {
            int mid = (low + hight) / 2;
            //中间的值 大于 要找的值，那么后面的值都大于他，所以mid后面的值要抛弃
            if (arr[mid] > item) {hight = mid - 1;}
            else if (arr[mid] < item) {low = mid + 1;}
            else return mid;
        }
        return -1;
    }
}
