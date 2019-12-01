package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Simple Contacts");
        primaryStage.setScene(new Scene(root, 550, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        ContactData.getInstance().saveContactList(ContactData.readPathFromLocation());
    }

    @Override
    public void init() throws Exception {
        ContactData.getInstance().loadContactList(ContactData.readPathFromLocation());
    }
}
