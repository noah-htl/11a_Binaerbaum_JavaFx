package at.htlsaalfelden.binaerbaum;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class HelloController {
    @FXML
    public TextField text;
    @FXML
    private AnchorPane pane;

    private int indent = 0;
    private int spacing = 0;
    private int currentX = 0;
    private int currentY = 0;

    private int n = 0;

    private BinaryTree tree;

    public void initialize() {
        tree = new BinaryTree();
    }

    private void callback(TreeNode node) {
        if(node == null) {
            calculateNewValues();
            return;
        }

        if(node.getData() != null) {

            StackPane label = createNode(node, currentX, currentY);

            double[] center = getMiddle(label);


            if (node.getParent() != null) {
                if (node == node.getParent().getLeft()) {
                    node.getParent().getLineLeft().setEndX(center[0]);
                    node.getParent().getLineLeft().setEndY(center[1]);
                }

                if (node == node.getParent().getRight()) {
                    node.getParent().getLineRight().setEndX(center[0]);
                    node.getParent().getLineRight().setEndY(center[1]);
                }
            }


            if (node.getLeft() != null) {
                Line line = new Line(center[0], center[1], 0, 0);
                node.setLineLeft(line);
                pane.getChildren().add(line);
            }
            if (node.getRight() != null) {
                Line line = new Line(center[0], center[1], 0, 0);
                node.setLineRight(line);
                pane.getChildren().add(line);
            }

            pane.getChildren().add(label);
        }

        currentX += spacing;
    }

    private StackPane createNode(TreeNode node, int x, int y) {
        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(new Circle(10, Color.AQUA));
        stackPane.getChildren().add(new Label(node.toString()));

        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);

        return stackPane;
    }

    private double[] getSize(StackPane stackPane) {
        double dx = stackPane.getWidth();
        double dy = stackPane.getHeight();

        return new double[] { dx, dy };
    }

    private double[] getMiddle(StackPane stackPane) {
        stackPane.applyCss();
        Bounds bounds = null;
        for(Node node : stackPane.getChildren()) {
            if (node instanceof Circle l) {
                bounds = l.getLayoutBounds();
                break;
            }
        }

        return new double[] { stackPane.getLayoutX() + bounds.getWidth() / 2, stackPane.getLayoutY() + bounds.getHeight() / 2 };
    }

    private void calculateNewValues() {
        final int mulX = 15;
        final int mulY = 20;

        n--;
        spacing = (int) Math.pow(2, n) * 10 + mulX;
        if(n == 0) {
            indent = 1;
        } else {
            indent = (int) Math.pow(2, n-1) - 1;
        }

        indent *= 10;

        indent += mulX;


        currentX = indent;
        currentY += mulY;
    }

    private void add(Integer data) {
        indent = 0;
        spacing = 0;
        currentY = 0;
        currentX = 0;
        n = 0;

        pane.getChildren().clear();

        tree.add(data);
        n = tree.getSize() + 1;
        calculateNewValues();
        tree.order(BinaryTree.ORDER.LEVEL, this::callback);
    }

    public void onKey(KeyEvent keyEvent) {
        if(Objects.equals(keyEvent.getCharacter(), "\r")) {
            try {
                add(Integer.parseInt(text.getText()));
                text.setText("");
            } catch (NumberFormatException e) {
                text.setText("");
            }
        }
    }
}