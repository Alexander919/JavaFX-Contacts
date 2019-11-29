package main;

import javafx.scene.control.Label;

import java.util.Objects;

public class AboutController {
    public Label version;

    private void setVersion() {
        version.setText(version.getText() + "1.0");
    }

    public void initialize() {
        setVersion();
    }
}
