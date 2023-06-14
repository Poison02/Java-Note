package cdu.zch.algorithm;

/**
 * 插入排序
 * @author Zch
 * @data 2023/6/14
 **/
public class InsertionSort {

    public static void insertionSort(int[] arr) {
        for (var i = 1; i < arr.length; i++) {
            var tmp = arr[i];
            var j = i - 1;

            while (j >= 0 && arr[j] > tmp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = tmp;
        }
    }

}
