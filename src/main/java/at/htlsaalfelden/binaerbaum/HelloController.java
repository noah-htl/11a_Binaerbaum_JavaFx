package at.htlsaalfelden.binaerbaum;

import at.htlsaalfelden.binaerbaum.BinaryTree.BinaryTree;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class HelloController {
    @FXML
    private Canvas canvas;

    private BinaryTree<Integer> binaryTree;

    public void initialize() {
        binaryTree = new BinaryTree<>();
        binaryTree.add(1);
        binaryTree.add(2);
        binaryTree.add(-1);
        binaryTree.add(0);
    }
}