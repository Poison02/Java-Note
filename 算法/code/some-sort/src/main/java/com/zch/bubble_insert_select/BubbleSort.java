package com.zch.bubble_insert_select;

/**
 * @author Zch
 * @date 2023/8/7
 **/
public class BubbleSort {

    public void bubbleSort(int[] nums, int n) {
        if (n <= 1) {
            return;
        }
        for (int i = 0; i < n; i++) {
            // 提前退出冒泡排序的标志位
            boolean flag = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                    // 表示有数据交换
                    flag = true;
                }
            }
            // 没有数据交换则退出循环
            if (!flag) {
                break;
            }
        }
    }

}
