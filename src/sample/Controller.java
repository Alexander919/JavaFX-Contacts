package sample;

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


    public void initialize() {

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
}
