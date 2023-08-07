package com.zch.bubble_insert_select;

/**
 * @author Zch
 * @date 2023/8/7
 **/
public class InsertSort {

    public void insertSort(int[] nums, int n) {
        if (n <= 1) {
            return;
        }
        for (int i = 1; i < n; i++) {
            int value = nums[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (nums[j] > value) {
                    nums[j + 1] = nums[j];
                } else {
                    break;
                }
            }
            nums[j + 1] = value;
        }
    }

}
