package com.zch.bubble_insert_select;

/**
 * @author Zch
 * @date 2023/8/7
 **/
public class SelectSort {

    public void selectSort(int[] nums, int n) {
        if (n <= 0) {
            return;
        }
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i; j < n; j++) {
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            int tmp = nums[min];
            nums[min] = nums[i];
            nums[i] = tmp;
        }
    }

}
