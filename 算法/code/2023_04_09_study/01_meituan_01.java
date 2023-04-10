package 2023_04_09_ study;

/**
 * 来自美团
 * 小团生日收到妈妈送的两个一模一样的数列作为礼物!他很开心的把玩，
 * 不过不小心没拿稳将数列摔坏了!现在他手上的两个数列分别为A和B，长度分别为n和m。
 * 小团很想再次让这两个数列变得一样。他现在能做两种操作:
 * 操作一是将一个选定数列中的某一个数a改成数b，这会花费lb-al的时间,操作二是选择一个数列中某个数a，
 * 将它从数列中丢掉，花费Ial的时间。小团想知道，他最少能以多少时间将这两个数列变得再次相同!
 * 输入描述;
 * 第一行两个空格隔开的正整数n和m，分别表示数列A和B的长度。接下来一行n个整数，分别为A1 A2...An
 * 接下来—行m个整数，分别为B1 B2...Bm
 * 对于所有数据，1 ≤ n,m ≤ 2000, IAil,lBil ≤ 10000输出一行一个整数，表示最少花费时间，来使得两个数列相同。
 */

public class 01_ meituan_01{

  // A B
  // zuo(A,B,0,0)
  // A[ai.....] 对应 B[bi.....]
  // 请变一样
  // 返回最小代价
  public static int func(int[]A,int[]B,int ai,int bi){

    if(ai==A.length&&bi==B.length){
      return 0;
    }
    if(ai!=A.length&&bi==B.length){
      return A[ai]+func(A,B,ai+1,bi);
    }
    if(ai==A.length&&bi!=B.length){
      return B[bi]+func(A,B,ai,bi+1);
    }

    // 可能性1：删掉A[ai]
    int p1=A[ai]+func(A,B,ai+1,bi);
    // 可能性2：删掉B[bi]
    int p2=B[bi]+func(A,B,ai,bi+1);
    // 可能性3：同时删掉
    int p3=Math.abs(A[ai]-B[bi])+func(A,B,ai+1,bi+1);

    return Math.min(p1,Math.min(p2,p3));

  }

}
