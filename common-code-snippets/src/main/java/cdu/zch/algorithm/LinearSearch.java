package cdu.zch.algorithm;

/**
 * 线性搜索
 * @author Zch
 * @data 2023/6/14
 **/
public class LinearSearch {

    public static int linearSearch(int[] arr, int target) {
        for (var i = 0; i < arr.length; i++) {
            if (target == arr[i]) {
                return i;
            }
        }
        return -1;
    }

}
