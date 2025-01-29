module at.htlsaalfelden.binaerbaum {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.htlsaalfelden.binaerbaum to javafx.fxml;
    exports at.htlsaalfelden.binaerbaum;
}