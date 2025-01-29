module at.htlsaalfelden.binaerbaum {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens at.htlsaalfelden.binaerbaum to javafx.fxml;
    exports at.htlsaalfelden.binaerbaum;
    exports at.htlsaalfelden.binaerbaum.BinaryTree;
    opens at.htlsaalfelden.binaerbaum.BinaryTree to javafx.fxml;
}