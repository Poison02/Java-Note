package cdu.zch.algorithm;

/**
 * 快速排序
 * @author Zch
 * @data 2023/6/14
 **/
public class QuickSort {

    public static void quickSort(int[] arr, int left, int right) {
        int pivotIndex = left + (right - left) / 2;
        int pivotValue = arr[pivotIndex];
        int i = left;
        int j = right;
        while (i <= j) {
            while (arr[i] < pivotValue) {
                i++;
            }
            while (arr[j] > pivotValue) {
                j--;
            }
            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
            if (left < i) {
                quickSort(arr, left, j);
            } else if (left > i) {
                quickSort(arr, i, right);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 9, 6, 3, 1, 1};
        quickSort(arr, 0, arr.length - 1);
        for (int i: arr) {
            System.out.println(i);
        }
    }

}
