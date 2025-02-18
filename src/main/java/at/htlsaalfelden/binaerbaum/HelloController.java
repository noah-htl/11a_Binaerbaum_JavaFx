package at.htlsaalfelden.binaerbaum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
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

        load(null);
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
        } else {
            StackPane label = createNode(new TreeNode(0), currentX, currentY);
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
        stackPane.applyCss();
        Bounds bounds = null;
        for(Node node : stackPane.getChildren()) {
            if (node instanceof Circle l) {
                bounds = l.getLayoutBounds();
                break;
            }
        }

        return new double[] { bounds.getWidth(), bounds.getHeight() };
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
        final int mul = 30;

        n--;
        spacing = (int) Math.pow(2, n) * mul;
        indent = ((int) Math.pow(2, n-1) - 1) * mul;

        currentX = indent;
        currentY += mul;
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

    @FXML
    public void scroll(ScrollEvent scrollEvent) {
        double zoomFactor = 1.05;
        double deltaY = scrollEvent.getDeltaY();
        if (deltaY < 0){
            zoomFactor = 2.0 - zoomFactor;
        }
        pane.setScaleX(pane.getScaleX() * zoomFactor);
        pane.setScaleY(pane.getScaleY() * zoomFactor);
    }

    private Double oldX = null;
    private Double oldY = null;

    @FXML
    public void drag(MouseEvent mouseEvent) {
        System.out.println("drag");


        if(oldX == null || oldY == null) {
            oldX = mouseEvent.getSceneX();
            oldY = mouseEvent.getSceneY();
            return;
        }

        double dx = mouseEvent.getSceneX() - oldX;
        double dy =  mouseEvent.getSceneY() - oldY;

        pane.setLayoutX(pane.getLayoutX() + dx * 10);
        pane.setLayoutY(pane.getLayoutY() + dy * 10);

        System.out.println(pane.getLayoutX());

        oldX = mouseEvent.getSceneX();
        oldY = mouseEvent.getSceneY();
    }

    private final File path = new File("data.json");

    @FXML
    public void save(ActionEvent actionEvent) {
        try {
            TreeLoader.save(path, tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void load(ActionEvent actionEvent) {
        try {
            tree = TreeLoader.load(path);

            indent = 0;
            spacing = 0;
            currentY = 0;
            currentX = 0;
            n = 0;

            pane.getChildren().clear();

            n = tree.getSize() + 1;
            calculateNewValues();
            tree.order(BinaryTree.ORDER.LEVEL, this::callback);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}