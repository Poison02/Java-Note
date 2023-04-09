package treenode.binarytree;

public class BinaryTree {

  private BinaryTreeNode root; // 创建二叉树

  /**
   * 初始化二叉树
   */
  public BinaryTree() {
  }

  /**
   * 初始化二叉树的另一种，加节点
   * 
   * @param root
   */
  public BinaryTree(BinaryTreeNode root) {
    this.root = root;
  }

  /**
   * 给二叉树加上节点
   * 
   * @param root
   */
  public void setRoot(BinaryTreeNode root) {
    this.root = root;
  }

  /**
   * 
   * @return 得到二叉树的节点
   */
  public BinaryTreeNode getRoot() {
    return root;
  }

  /**
   * 二叉树的清空：
   * 首先提供一个清空以某个节点为根节点的子树的方法，即递归地删除每个节点；
   * 接着提供一个删除树的方法，直接通过第一种方法删除到根节点即可。
   * 
   * @param node 节点
   */
  public void clear(BinaryTreeNode node) {
    if (node != null) {
      clear(node.getLeftChild()); // 删除左节点
      clear(node.getRightChild()); // 删除右节点
      node = null; // 删除自身
    }
  }

  /**
   * 清空树
   * public void clear() {
   * clear(root);
   * }
   */

  /**
   * 判断二叉树是否为空
   * 
   * @return
   */
  public boolean isEmpty() {
    return this.root == null;
  }

  /**
   * 计算二叉树的高度，使用递归实现
   * 如果一个节点为空，那么这个节点肯定是一棵空树，高度为0；
   * 若不为空，则遍历左右子树，最后加上自身的高度即可。
   * 
   * @param node 节点
   * @return
   */
  public int height(BinaryTreeNode node) {
    if (node == null) {
      return 0;
    } else {
      int l = height(node.getLeftChild());
      int r = height(node.getRightChild());
      return l > r ? (l + 1) : (r + 1);
    }
  }

  /**
   * 获取二叉树的高度
   * public int heigh() {
   * return height(root);
   * }
   */

  /**
   * 计算二叉树的节点数，递归实现。
   * 节点为空，则节点数为0；
   * 不为空，就遍历左右子节点，加上左右节点数以及自身即为节点数。
   * 
   * @param node
   * @return
   */
  public int size(BinaryTreeNode node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + size(node.getLeftChild()) + size(node.getRightChild());
    }
  }

  /**
   * 获取二叉树的节点数
   * public int size() {
   * return size(root);
   * }
   */

  /**
   * 查找node节点在subTree子树中的父结点
   * 
   * @param subTree
   * @param node
   * @return
   */
  public BinaryTreeNode getParent(BinaryTreeNode subTree, BinaryTreeNode node) {
    if (subTree == null) {
      return null;
    }
    if (subTree.getLeftChild() == node || subTree.getRightChild() == node) {
      return subTree;
    }
    BinaryTreeNode parent = null;
    if (getParent(subTree.getLeftChild(), node) != null) {
      parent = getParent(subTree.getLeftChild(), node);
      return parent;
    } else {
      return getParent(subTree.getRightChild(), node);
    }
  }

  /**
   * 查找node节点在二叉树中的父结点
   * public BinaryTreeNode getParent(BinaryTreeNode node) {
   * return (root == null || root == node) ? null: getParent(root, node);
   * }
   */

  /**
   * 返回某个节点的左子树
   * 
   * @param node
   * @return
   */
  public BinaryTreeNode getLeftTree(BinaryTreeNode node) {
    return node.getLeftChild();
  }

  /**
   * 返回某个节点的右子树
   * 
   * @param node
   * @return
   */
  public BinaryTreeNode getRightTree(BinaryTreeNode node) {
    return node.getRightChild();
  }

  /**
   * 二叉树的插入分析：
   * 插入某个节点的左子节点
   * 插入某个节点的右子节点
   * 当这个节点本身有子节点时，这样的插入会覆盖原来在这个位置上的节点。但是子节点也能代表一棵子树
   * 虽然插入的是一个节点，但也有可能插入很多个节点（插入的是一棵子树）
   * 给某个节点插入左节点
   * 
   * @param parent
   * @param newNode
   */
  public void insertLeft(BinaryTreeNode parent, BinaryTreeNode newNode) {
    parent.setLeftChild(newNode);
  }

  /**
   * 给某个节点插入右节点
   * 
   * @param parent
   * @param newNode
   */
  public void insertRight(BinaryTreeNode parent, BinaryTreeNode newNode) {
    parent.setRightChild(newNode);
  }

  /**
   * 二叉树的遍历
   * 先序遍历
   * 中序遍历
   * 后序遍历
   * 
   * 先序遍历：先访问根节点，然后是左节点、右节点
   * 
   * @param node
   */
  public void PreOrder(BinaryTreeNode node) {
    if (node != null) {
      System.out.println(node.getData()); // 访问根节点
      PreOrder(node.getLeftChild()); // 遍历左子树
      PreOrder(node.getRightChild()); // 遍历右子树
    }
  }

  /**
   * 中序遍历：先遍历左子树，然后根节点、右节点
   * 
   * @param node
   */
  public void InOrder(BinaryTreeNode node) {
    if (node != null) {
      InOrder(node.getLeftChild()); // 先遍历左子树
      System.out.println(node.getData()); // 访问根节点
      InOrder(node.getRightChild()); // 遍历右子树
    }
  }

  /**
   * 后序遍历：先遍历左子树，然后右节点、根节点
   * 
   * @param node
   */
  public void PostOrder(BinaryTreeNode node) {
    if (node != null) {
      PostOrder(node.getLeftChild()); // 先遍历左子树
      PostOrder(node.getRightChild()); // 遍历右子树
      System.out.println(node.getData()); // 访问根节点
    }
  }
}
