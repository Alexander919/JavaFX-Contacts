package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {
    public TableView<Contact> tableView;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField phoneNumberField;
    public TextField notesField;
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
}
