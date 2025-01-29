package at.htlsaalfelden.binaerbaum.BinaryTree;

public interface TreeNode<T> extends Comparable<TreeNode<T>> {
    T getData();

    TreeNode<T> getLeft();
    void setLeft(TreeNode<T> node);
    TreeNode<T> getRight();
    void setRight(TreeNode<T> node);

    int getLevelFromTop();
    void setLevelFromTop(int level);
}
