package cdu.zch.algorithm;

/**
 * 冒泡排序
 * @author Zch
 * @data 2023/6/14
 **/
public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        int lastIndex = arr.length - 1;
        var tmp = 1;

        for (int j = 0; j < lastIndex; j++) {
            for (int i = 0; i < lastIndex - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 9, 6, 3, 1, 1};
        bubbleSort(arr);
        for (int i: arr) {
            System.out.println(i);
        }
    }

}
