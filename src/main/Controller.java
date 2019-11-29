package main;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    public TableView<Contact> tableView;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField phoneNumberField;
    public TextField notesField;
    public MenuItem exitMenuItem;
    public MenuItem aboutMenuItem;
    public BorderPane borderPane;
    ObservableList<Contact> observableList;


    public void initialize() {
        setTestingValues();
    }

    public void addPerson(ActionEvent actionEvent) {
        ObservableList<Contact> data = tableView.getItems();
        data.add(new Contact(firstNameField.getText(), lastNameField.getText(),
                phoneNumberField.getText(), notesField.getText()));

        firstNameField.setText("");
        lastNameField.setText("");
        phoneNumberField.setText("");
        notesField.setText("");
    }

    public void setTestingValues() {
        observableList = FXCollections.observableArrayList(
                new Contact("John", "Smith", "123456", "hello John"),
                new Contact("Mary", "Doe", "654321", "hello Mary"),
                new Contact("Isabella", "Williams", "2346541", "hello Isabella"),
                new Contact("Alex", "Brown", "7890123", "how you doing?")
        );
        tableView.setItems(observableList);
    }

    public void exitApp(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void aboutPopUp(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = createDialog("About");
        FXMLLoader fxmlLoader = createFXMLloader("about.fxml");
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<ButtonType> result = dialog.showAndWait();
//        System.out.println(tableView.getSelectionModel().getSelectedItem().getFirstName());

    }
    public FXMLLoader createFXMLloader(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));
        return fxmlLoader;
    }

    public Dialog<ButtonType> createDialog(String title) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle(title);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        return dialog;
    }
}