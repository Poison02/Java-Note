package cdu.zch.algorithm;

/**
 * 二分搜索
 * @author Zch
 * @data 2023/6/14
 **/
public class BinarySearch {

    /**
     * 二分法的第一种写法，左闭右闭区间
     * @param arr
     * @param target
     * @return
     */
    public static int binarySearch1(int[] arr, int target) {
        var left = 0;
        var right = arr.length - 1;
        while (left <= right) {
            var mid = left + right >> 1;
            if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 二分法的第二种写法，左闭右开区间
     * @param arr
     * @param target
     * @return
     */
    public static int binarySearch2(int[] arr, int target) {
        var left = 0;
        var right = arr.length - 1;
        while (left < right) {
            var mid = left + right >> 1;
            if (arr[mid] > target) {
                right = mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 二分法的第三种写法，递归写法
     * @param arr
     * @param target
     * @return
     */
    public static int binarySearch3(int[] arr, int left, int right, int target) {
        if (right >= left) {
            var mid = left + right >> 1;
            if (arr[mid] == target) {
                return mid;
            }

            if (arr[mid] > target) {
                return binarySearch3(arr, left, mid - 1, target);
            }
            return binarySearch3(arr, mid + 1, right, target);
        }
        return -1;
    }

}
