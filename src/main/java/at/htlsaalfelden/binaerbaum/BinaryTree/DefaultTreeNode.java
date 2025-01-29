package at.htlsaalfelden.binaerbaum.BinaryTree;

public class DefaultTreeNode<T extends Comparable<T>> implements TreeNode<T> {
    private T data;
    private TreeNode<T> left;
    private TreeNode<T> right;
    private int levelFromTop;

    public DefaultTreeNode(T data) {
        this.data = data;
        this.levelFromTop = 0;
    }


    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public TreeNode<T> getLeft() {
        return this.left;
    }

    @Override
    public void setLeft(TreeNode<T> node) {
        this.left = node;
    }

    @Override
    public TreeNode<T> getRight() {
        return this.right;
    }

    @Override
    public void setRight(TreeNode<T> node) {
        this.right = node;
    }

    @Override
    public int getLevelFromTop() {
        return this.levelFromTop;
    }

    @Override
    public void setLevelFromTop(int level) {
        this.levelFromTop = level;
    }

    @Override
    public int compareTo(TreeNode<T> o) {
        return this.data.compareTo(o.getData());
    }
}
