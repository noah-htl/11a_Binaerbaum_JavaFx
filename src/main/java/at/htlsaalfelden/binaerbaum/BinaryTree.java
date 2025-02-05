package at.htlsaalfelden.binaerbaum;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree {
    private TreeNode top;
    private int size = 1;

    public void add(int v) {
        TreeNode node = new TreeNode(v);
        if(top == null) {
            top = node;
            return;
        }
        int insertionLevel = add(node, top, 1);
        if(insertionLevel > size) {
            size = insertionLevel;
        }
    }

    private int add(TreeNode node, TreeNode root, int parentLevel) {
        if(node.getData() < root.getData()) {
            if(root.getLeft() == null) {
                root.setLeft(node);
                node.setParent(root);
                return parentLevel+1;
            }
            return add(node, root.getLeft(), parentLevel+1);
        } else {
            if(root.getRight() == null) {
                root.setRight(node);
                node.setParent(root);
                return parentLevel+1;
            }
            return add(node, root.getRight(), parentLevel+1);
        }
    }

    public void order(ORDER order, Consumer<TreeNode> callback) {
        if(order == ORDER.LEVEL) {
            levelOrder(callback, List.of(top));
        }
        order(order, callback, top);
    }

    private void levelOrder(Consumer<TreeNode> callback, List<TreeNode> nodes) {
        List<TreeNode> newNodes = new ArrayList<>();

        int i = 0;

        for(TreeNode node : nodes) {
            if(node.getLeft() != null) {
                newNodes.add(node.getLeft());
                i++;
            } else {
                newNodes.add(TreeNode.empty());
            }
            if(node.getRight() != null) {
                newNodes.add(node.getRight());
                i++;
            } else {
                newNodes.add(TreeNode.empty());
            }

            callback.accept(node);
        }

        callback.accept(null);

        if(i > 0) {
            levelOrder(callback, newNodes);
        }
    }

    private void order(ORDER order, Consumer<TreeNode> callback, TreeNode node) {
        if(node == null) {
            return;
        }
        if(order == ORDER.PRE) {
            callback.accept(node);
            order(order, callback, node.getLeft());
            order(order, callback, node.getRight());
        }
        if(order == ORDER.IN) {
            order(order, callback, node.getLeft());
            callback.accept(node);
            order(order, callback, node.getRight());
        }
        if(order == ORDER.POST) {
            order(order, callback, node.getLeft());
            order(order, callback, node.getRight());
            callback.accept(node);
        }
    }

    public void afterInit() {
        order(ORDER.LEVEL, new Consumer<TreeNode>() {
            @Override
            public void accept(TreeNode node) {
                if(node == null || node.getData() == null) {
                    return;
                }

                if(node.getLeft() != null) {
                    node.getLeft().setParent(node);
                }

                if(node.getRight() != null) {
                    node.getRight().setParent(node);
                }
            }
        });
    }

    public int getSize() {
        return size;
    }

    public enum ORDER {
        IN,
        PRE,
        POST,
        LEVEL
    }
}
