package main;

import javafx.application.Platform;
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
    public MenuItem saveMenuItem;
//    ObservableList<Contact> observableList;


    public void initialize() {
//        setTestingValues();
        tableView.setItems(ContactData.getInstance().getObservableList());
    }
//todo Implement New functionality. Just empty contact list and reset all paths

//    public void setTestingValues() {
//        observableList = FXCollections.observableArrayList(
//                new Contact("John", "Smith", "123456", "hello John"),
//                new Contact("Mary", "Doe", "654321", "hello Mary"),
//                new Contact("Isabella", "Williams", "2346541", "hello Isabella"),
//                new Contact("Alex", "Brown", "7890123", "how you doing?")
//        );
//    }

    //adds a Person object to the ObservableList when you press the Add button
    public void addPerson(ActionEvent actionEvent) {
        ObservableList<Contact> data = tableView.getItems();
        data.add(new Contact(firstNameField.getText(), lastNameField.getText(),
                phoneNumberField.getText(), notesField.getText()));

        clearFields();
    }

    public void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        phoneNumberField.setText("");
        notesField.setText("");
    }

    public void exitApp(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void aboutPopUp(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = createDialog("About");
        FXMLLoader fxmlLoader = createFXMLLoaded("about.fxml");
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<ButtonType> result = dialog.showAndWait();
//        System.out.println(tableView.getSelectionModel().getSelectedItem().getFirstName());

    }

    //todo Implement Help

    public FXMLLoader createFXMLLoaded(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));
        return fxmlLoader;
    }

    public Dialog<ButtonType> createDialog(String title) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle(title);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        return dialog;
    }

    public void saveList(ActionEvent actionEvent) throws IOException {
        ContactData.getInstance().openFileChooser(borderPane, "save");
    }
    //todo implement loadList method

    //todo implement modifiable cells
}
