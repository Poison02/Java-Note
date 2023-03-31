public class 两数相加 {

  /**
   * 
   * 解法一：使用模拟实现
   * 
   */
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

    ListNode dummy = new ListNode();
    ListNode cur = dummy;
    int carry = 0;
    int sum = 0;
    while (l1 != null || l2 != null || carry != 0) {
      sum = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
      carry = sum / 10;

      cur.next = new ListNode(sum % 10);
      cur = cur.next;

      l1 = l1 == null ? null : l1.next;
      l2 = l2 == null ? null : l2.next;
    }

    return dummy.next;
  }

  /**
   * 
   * 解法二：也是类似模拟，但是是使用递归实现
   * 在主方法中调用时，这样做：add(l1, l2, 0)
   * 
   */

  public ListNode add(ListNode l1, ListNode l2, int carry) {

    if (l1 == null && l2 == null && carry ==0) {
      return null;
    }

    int val = carry;
    if (l1 != null) {
      val += l1.val;
      l1 = l1.next;
    }

    if (l2 != null) {
      val += l2.val;
      l2 = l2.next;
    }

    ListNode newNode = new ListNode(val % 10);
    newNode = add(l1, l2, val / 10);

    return newNode;
  }
}
