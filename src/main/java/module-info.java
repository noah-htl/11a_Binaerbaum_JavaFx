module at.htlsaalfelden.binaerbaum {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires com.google.gson;


    opens at.htlsaalfelden.binaerbaum;
    exports at.htlsaalfelden.binaerbaum;
}