package com.lp;

public class TD {


    public static Integer searchBinary(Integer[] arr, Integer key) {

        int low = 0, height = arr.length - 1;


        while (height >= low) {
            int mid = (low + height) / 2;
            if (arr[mid] > key)
                height = mid - 1;
            else if (arr[mid] < key)
                low = mid + 1;
            return mid;
        }
        return -1;
    }
}
