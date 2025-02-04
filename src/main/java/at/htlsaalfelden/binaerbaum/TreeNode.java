package at.htlsaalfelden.binaerbaum;

import javafx.scene.shape.Line;

public class TreeNode {
    private final Integer data;
    private TreeNode left;
    private TreeNode right;

    private TreeNode parent;

    private Line lineLeft;
    private Line lineRight;

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public Integer getData() {
        return data;
    }

    public Line getLineLeft() {
        return lineLeft;
    }

    public void setLineLeft(Line lineLeft) {
        this.lineLeft = lineLeft;
    }

    public Line getLineRight() {
        return lineRight;
    }

    public void setLineRight(Line lineRight) {
        this.lineRight = lineRight;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode(int data) {
        this.data = data;
    }

    public static TreeNode empty() {
        return new TreeNode(null);
    }

    private TreeNode(Integer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(this.data == null) {
            return "#";
        }
        return this.data.toString();
    }
}
