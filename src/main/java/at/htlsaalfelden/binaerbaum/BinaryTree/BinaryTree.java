package at.htlsaalfelden.binaerbaum.BinaryTree;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class BinaryTree<T extends Comparable<T>> {

    private TreeNode<T> root;
    private int depth;

    private Function<T,? extends TreeNode<T>> defaultConstructor = DefaultTreeNode<T>::new;

    public BinaryTree() {
        this(DefaultTreeNode<T>::new);
    }

    public BinaryTree(Function<T,? extends TreeNode<T>> constructor) {
        this((TreeNode<T>) null, constructor);
    }

    public BinaryTree(TreeNode<T> root, Function<T,? extends TreeNode<T>> constructor) {
        this.root = root;
        this.depth = 0;
        this.defaultConstructor = constructor;
    }

    public BinaryTree(T root, Function<T,? extends TreeNode<T>> constructor) {
        this(constructor.apply(root), constructor);
    }

    public TreeNode<T> getRoot() {
        return this.root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public void setRoot(T root) {
        this.root = this.defaultConstructor.apply(root);
    }

    public void add(T data) {
        this.add(this.defaultConstructor.apply(data));
    }

    public void add(TreeNode<T> node) {
        if(this.getRoot() == null) {
            this.setRoot(node);
            node.setLevelFromTop(1);
            this.depth = 1;
        } else {
            int insertionDepth = add(node, this.getRoot(), 2);
            if(insertionDepth > this.depth) {
                this.depth = insertionDepth;
            }
        }
    }

    private int add(TreeNode<T> node, TreeNode<T> root, int levelStart) {
        if(node.getData().compareTo(root.getData()) < 0) {
            if(root.getLeft() == null) {
                node.setLevelFromTop(root.getLevelFromTop() + 1);
                root.setLeft(node);
                return levelStart;
            } else {
                return add(node, root.getLeft(), levelStart+1);
            }
        } else {
            if(root.getRight() == null) {
                node.setLevelFromTop(root.getLevelFromTop() + 1);
                root.setRight(node);
                return levelStart;
            } else {
                return add(node, root.getRight(), levelStart+1);
            }
        }
    }

    public void inOrder(Consumer<T> consumer) {
        inOrder(consumer, this.getRoot());
    }
    public void preOrder(Consumer<T> consumer) {
        preOrder(consumer, this.getRoot());
    }
    public void postOrder(Consumer<T> consumer) {
        postOrder(consumer, this.getRoot());
    }
    public void levelOrder(Consumer<LevelData<T>> consumer) {
        levelOrder(consumer, List.of(this.getRoot()));
    }

    private void inOrder(Consumer<T> consumer, TreeNode<T> root) {
        if(root == null) {
            return;
        }
        inOrder(consumer, root.getLeft());
        consumer.accept(root.getData());
        inOrder(consumer, root.getRight());
    }

    private void preOrder(Consumer<T> consumer, TreeNode<T> root) {
        if(root == null) {
            return;
        }
        consumer.accept(root.getData());
        preOrder(consumer, root.getLeft());
        preOrder(consumer, root.getRight());
    }

    private void postOrder(Consumer<T> consumer, TreeNode<T> root) {
        if(root == null) {
            return;
        }
        postOrder(consumer, root.getLeft());
        postOrder(consumer, root.getRight());
        consumer.accept(root.getData());
    }

    public int getDepth() {
        return depth;
    }

    private void levelOrder(Consumer<LevelData<T>> consumer, List<TreeNode<T>> roots) {
        List<TreeNode<T>> nodes = new ArrayList<>();

        for (int i = 0; i < roots.size(); i++) {
            TreeNode<T> node = roots.get(i);

            if(i == roots.size() - 1) {
                consumer.accept(new LevelData<>(node.getData(), true));
            } else {
                consumer.accept(new LevelData<>(node.getData(), false));
            }

            if(node.getLeft() != null) {
                nodes.add(node.getLeft());
            }
            if(node.getRight() != null) {
                nodes.add(node.getRight());
            }
        }

        if(!nodes.isEmpty()) {
            levelOrder(consumer, nodes);
        }


    }

    public record LevelData<T>(T data, boolean isLastInLevel) {
        @Override
        public String toString() {
            if(isLastInLevel) {
                return data + "\n";
            }
            return data + "";
        }
    }
}
