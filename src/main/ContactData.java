package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;

public class ContactData {
    private static ContactData contactData = new ContactData();
    private ObservableList<Contact> observableList;
    //saved to the location specified by the user when the program is closed
    //default location is program's location
    public static File fileLocation = new File("contacts.dat");

    //temporary
    ContactData() {
        observableList = FXCollections.observableArrayList(
                new Contact("John", "Smith", "123456", "hello John"),
                new Contact("Mary", "Doe", "654321", "hello Mary"),
                new Contact("Isabella", "Williams", "2346541", "hello Isabella"),
                new Contact("Alex", "Brown", "7890123", "how you doing?")
        );
    }

    static ContactData getInstance() {
        return contactData;
    }

    public ObservableList<Contact> getObservableList() {
        return observableList;
    }

    public void loadContactList(File file) {

    }

    public void openFileChooser(BorderPane bp, String what) throws IOException {
        FileChooser fc = new FileChooser();
        Window window = bp.getScene().getWindow();
        File file;

        if (what.equals("save")) {
            file = fc.showSaveDialog(window);
            saveContactList(file);
        } else {
            file = fc.showOpenDialog(window);
            loadContactList(file);
        }
    }

    public void saveContactList(File file) throws IOException {
        //user pressed cancel
        if (file == null) return;
        fileLocation = file;

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        for (Contact contact : observableList) {
            pw.write(String.format("#%s\n%s\n%s\n%s\n\n", contact.getFirstName(),
                    contact.getLastName(), contact.getPhoneNumber(), contact.getContactNotes()));
            pw.flush();
        }
    }
}
