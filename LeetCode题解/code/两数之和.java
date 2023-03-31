import java.util.HashMap;
import java.util.Map;

public class 两数之和 {
  public int[] twoSums(int[] nums, int target) {

    // 定义哈希表存储数组值和对应的下标
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < nums.length; i ++) {
      int x = nums[i];
      int y = target - nums[i];
      if (map.containsKey(y)) {
        return new int[] {map.get(y), i};
      }
      map.put(x, i);
    }

    return new int[0];
  }
}