import java.util.HashSet;
import java.util.Set;

public class 无重复字符的最长字串 {
  public int lengthOfLongestSubstring(String s) {
    Set<Character> set = new HashSet<>();
    int i = 0, ans = 0;

    for (int j = 0; j < s.length(); ++j) {
      char c = s.charAt(j);
      while (set.contains(c)) {
        set.remove(s.charAt(i++));
      }
      set.add(c);
      ans = Math.max(ans, j - i + 1);
    }

    return ans;
  }
}