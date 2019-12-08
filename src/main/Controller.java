package main;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    public TableView<Contact> tableView;
    public BorderPane borderPane;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField phoneNumberField;
    public TextField notesField;
    public MenuItem exitMenuItem;
    public MenuItem aboutMenuItem;
    public MenuItem saveMenuItem;
    public TableColumn<Contact, String> firstNameCol;
    public TableColumn<Contact, String> lastNameCol;
    public TableColumn<Contact, String> phoneNumCol;
    public TableColumn<Contact, String> notesCol;

    public void initialize() {
        tableView.setItems(ContactData.getInstance().getObservableList());

        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setFirstName(event.getNewValue()));

        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setLastName(event.getNewValue()));

        phoneNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setPhoneNumber(event.getNewValue()));

        notesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        notesCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setContactNotes(event.getNewValue()));

    }

    //    public void setTestingValues() {
//        observableList = FXCollections.observableArrayList(
//                new Contact("John", "Smith", "123456", "hello John"),
//                new Contact("Mary", "Doe", "654321", "hello Mary"),
//                new Contact("Isabella", "Williams", "2346541", "hello Isabella"),
//                new Contact("Alex", "Brown", "7890123", "how you doing?")
//        );
//    }
    public static Optional<ButtonType> alert(String title, String header, String content) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait();
    }

    //adds a Person object to the ObservableList when you press the Add button
    public void addPerson() {
        if (firstNameField.getText().isEmpty()) {
            Bounds textFieldBounds = firstNameField.getBoundsInLocal();
            Point2D tooltipLocation = firstNameField.localToScreen(textFieldBounds.getMaxX(), textFieldBounds.getMinY());

            Tooltip tooltip = new Tooltip("First name can't be empty");
            tooltip.show(borderPane.getScene().getWindow(), tooltipLocation.getX(), tooltipLocation.getY());
            tooltip.setAutoHide(true);
            return;
        }

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

    public void exitApp() {
        Platform.exit();
    }

    public void aboutPopUp() {
        Dialog<ButtonType> dialog = createDialog("About");
        FXMLLoader fxmlLoader = createFXMLLoaded("about.fxml");
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.showAndWait();
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

    public void saveList() throws IOException {
        ContactData.getInstance().openFileChooser(borderPane, "save");
    }

    public void loadList() throws IOException {
        ContactData.getInstance().openFileChooser(borderPane, "load");
    }

    public void newList() throws IOException {
        ContactData.getInstance().newContactList();
    }

    public void handleKeyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            addPerson();
        }

        if (keyEvent.getCode() == KeyCode.DELETE) {
            deleteContact(tableView.getSelectionModel().getSelectedItem());
        }
    }

    public void deleteContact(Contact contact) {
        String title = "Delete contact";
        String header = "You are about to delete contact " + contact.getFirstName();
        String content = "Are you sure?";

        Optional<ButtonType> result = alert(title, header, content);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactData.getInstance().getObservableList().remove(contact);
        }
    }
}
