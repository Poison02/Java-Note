package treenode;

public class MyBinaryTree {

  private char data;
  private MyBinaryTree rightChild;
  private MyBinaryTree leftChild;

  public MyBinaryTree(char data) {
    this.data = data;
    this.leftChild = null;
    this.rightChild = null;
  }

  public void setLeftChild(char data) {
    this.leftChild = new MyBinaryTree(data);
  }

  public void setRightChild(char data) {
    this.rightChild = new MyBinaryTree(data);
  }

  public void setChild(char leftData, char rightData) {
    this.leftChild = new MyBinaryTree(leftData);
    this.rightChild = new MyBinaryTree(rightData);
  }

  /**
   * 中序遍历
   */
  public void inOrderTraversal() {
    if (this.leftChild != null) {
      this.leftChild.inOrderTraversal();
    }
    System.out.print("[" + this.data + "]");
    if (this.rightChild != null) {
      this.rightChild.inOrderTraversal();
    }
  }

  /**
   * 先序遍历
   */
  public void preOrderTraversal() {
    System.out.print("[" + this.data + "]");
    if (this.leftChild != null) {
      this.leftChild.inOrderTraversal();
    }
    if (this.rightChild != null) {
      this.rightChild.inOrderTraversal();
    }
  }

  /**
   * 后序遍历
   */
  public void postOrderTraversal() {
    if (this.leftChild != null) {
      this.leftChild.inOrderTraversal();
    }
    if (this.rightChild != null) {
      this.rightChild.inOrderTraversal();
    }
    System.out.print("[" + this.data + "]");
  }

  public MyBinaryTree getLeftChild() {
    return this.leftChild;
  }

  public MyBinaryTree getRightChild() {
    return this.rightChild;
  }

  public static void main(String[] args) {
    MyBinaryTree root = new MyBinaryTree('-');
    root.setLeftChild('+');
    root.setRightChild('/');
    root.getLeftChild().setLeftChild('a');
    root.getLeftChild().setRightChild('*');
    root.getRightChild().setLeftChild('e');
    root.getRightChild().setRightChild('f');
    root.getLeftChild().getRightChild().setChild('b', '-');
    root.getLeftChild().getRightChild().getRightChild().setChild('c', 'd');
  
    System.out.println("前序遍历：");
    root.preOrderTraversal();
    System.out.println("\n中序遍历：");
    root.inOrderTraversal();
    System.out.println("\n后序遍历：");
    root.postOrderTraversal();

    /**
     * 
     * 构建的树结构：
     *          -
     *     +         /
     * a      *    e    f
     *      b   -
     *         c  d
     * 
     * 前序遍历：
       [-][a][+][b][*][c][-][d][e][/][f]
       中序遍历：
       [a][+][b][*][c][-][d][-][e][/][f]
       后序遍历：
       [a][+][b][*][c][-][d][e][/][f][-]
     * 
     */
  }
}
