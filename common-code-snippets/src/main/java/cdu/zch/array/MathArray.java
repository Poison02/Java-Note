package cdu.zch.array;

import java.util.Arrays;

/**
 * 常用与数学相关
 * @author Zch
 * @data 2023/6/15
 **/
public class MathArray {

    // 计算数组的所有元素是否相等
    public static <T> boolean allEqual(T[] arr) {
        return Arrays.stream(arr).distinct().count() == 1;
    }

    // 找到int数组中的平均值
    public static double arrayMean(int[] arr) {
        return (double) Arrays.stream(arr).sum() / arr.length;
    }

    // 找到int数组的中位数
    public static double arrayMedian(int[] arr) {
        Arrays.sort(arr);
        var mid = arr.length / 2;
        return arr.length % 2 != 0 ? (double) arr[mid] : (double) (arr[mid] + arr[mid - 1]) / 2;
    }

    // int数组的总和
    public static int arraySum(int[] arr) {
        return Arrays.stream(arr).sum();
    }

    // int数组的最大值
    public static int findMax(int[] arr) {
        return Arrays.stream(arr).reduce(Integer.MAX_VALUE, Integer::max);
    }

    // int数组的最小值
    public static int findMin(int[] arr) {
        return Arrays.stream(arr).reduce(Integer.MIN_VALUE, Integer::min);
    }
}
