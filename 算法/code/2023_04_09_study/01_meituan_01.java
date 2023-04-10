package 2023_04_09_study;

public class 01_meituan_01 {
  
  public static int func(int[] A, int[] B, int ai, int bi) {

    if (ai == A.length && bi == B.length) {
      return 0;
    }
    if (ai != A.length && bi == B.length) {
      return A[ai] + func(A, B, ai + 1, bi);
    }
    if (ai == A.length && bi != B.length) {
      return B[bi] + func(A, B, ai, bi + 1);
    }

    int p1 = A[ai] + func(A, B, ai + 1, bi);
    int p2 = B[bi] + func(A, B, ai, bi + 1);
    int p3 = Math.abs(A[ai] - B[bi]) + func(A, B, ai + 1, bi + 1);

    return Math.min(p1, Math.min(p2, p3));
  }
}
