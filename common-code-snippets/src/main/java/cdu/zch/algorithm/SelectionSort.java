package cdu.zch.algorithm;

/**
 * 选择排序
 * @author Zch
 * @data 2023/6/14
 **/
public class SelectionSort {

    public static void selectionSort(int[] arr) {
        int len = arr.length;

        for (int i = 0; i < len - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 9, 6, 3, 1, 1};
        selectionSort(arr);
        for (int i: arr) {
            System.out.println(i);
        }
    }

}
