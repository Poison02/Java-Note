package treenode.binarytree;

/**
 * 使用递归实现二叉树
 * 二叉树的左右链表表示法
 * */ 
public class BinaryTreeNode {

  private int data; // 数据
  private BinaryTreeNode leftChild; // 左节点
  private BinaryTreeNode rightChild; // 右节点

  /**
   * @return 得到节点的数值
   */
  public int getData() {
    return data;
  }

  /**
   * 设置节点数值
   * @param data
   * 
   */
  public void setData(int data) {
    this.data = data;
  }

  /**
   * 
   * @return 得到左子节点
   */
  public BinaryTreeNode getLeftChild() {
    return leftChild;
  }

  /**
   * 
   * @return 得到右子节点
   */
  public BinaryTreeNode getRightChild() {
    return rightChild;
  }

  /**
   * 设置左节点
   * @param leftChild
   */
  public void setLeftChild(BinaryTreeNode leftChild) {
    this.leftChild = leftChild;
  }

  /**
   * 设置右节点
   * @param rightChild
   */
  public void setRightChild(BinaryTreeNode rightChild) {
    this.rightChild = rightChild;
  }
}
