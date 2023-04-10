package 2023_04_09_study;

/**
 * 来自神策
 * 给定一个数组arr，表示连续n天的股价，数组下标表示第几天
 * 指标X：任意两天的股价之和 - 此两天间隔的天数
 * 比如：
 *   第3天，价格是10
 *   第9天，价格是30
 *   那么，第三天和第九天的指标X = 10 + 30 - （9 - 3） = 34
 *   返回arr中的最大的指标X
 *   时间复杂度O（n）
 * 
 * 分析：假设 i 在 j 前面
 * 由题意可得：arr[i] + arr[j] - (j - i)
 *                 可变换为：arr[i] + i + arr[j] - j
 * 相当于求arr[i] + i 的最大值
 * 只需要定一个变量保存 arr[i] + i，然后遍历数组，依次更新最大值以及前面保存好的变量。都取最大值即可
 */
public class 01_shence {
  
  public static int maxX(int[] arr) {
    // 当数组为空或者长度小于2时，不符合题意直接返回-1
    if (arr == null || arr.length < 2) {
      return -1;
    }
    
    // 定义一个变量保存之前最好的
    int preBest = arr[0];
    int ans = 0;
    for (int i = 1; i < arr.length; i ++) {
      ans = Math.max(ans, arr[i] - i + preBest);
      preBest = Math.max(preBest, arr[i] + i);
    }

    return ans;
  }
}
