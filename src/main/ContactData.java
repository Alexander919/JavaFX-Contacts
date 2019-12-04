package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ContactData {
    private int fields = 4;
    private String defaultContacts = "contacts.dat";
    private static ContactData contactData = new ContactData();
    private ObservableList<Contact> observableList = FXCollections.observableArrayList();

    public static Path location = Paths.get("location.dat");
    //saved to the location specified by the user when the program is closed
    //default location is program's location
    public static File contactsFile;

    //temporary
//    ContactData() {
//        observableList = FXCollections.observableArrayList(
//                new Contact("John", "Smith", "123456", "hello John"),
//                new Contact("Mary", "Doe", "654321", "hello Mary"),
//                new Contact("Isabella", "Williams", "2346541", "hello Isabella"),
//                new Contact("Alex", "Brown", "7890123", "how you doing?")
//        );
//    }

    static ContactData getInstance() {
        return contactData;
    }

    public void setContactsFile(File file) {
        contactsFile = file;
    }

    public ObservableList<Contact> getObservableList() {
        return observableList;
    }

    //read a path to contacts from location.dat
    public static File readPathFromLocation() throws IOException {
        BufferedReader br = Files.newBufferedReader(location);
        return new File(br.readLine());
    }

    public void loadContactList(File file) throws IOException {
        if (file == null) return;

        BufferedReader br = new BufferedReader(new FileReader(file));
        //array to store read data
        ArrayList<String[]> data = new ArrayList<>();
        //string to store a piece of data
        String piece;
        //index variable (i % 4)
        int i = 0;

        do {
            String[] contact = new String[fields];

            while (true) {
                piece = br.readLine();

                if (piece != null && !piece.equals("")) {
                    contact[i++ % fields] = piece;

                } else
                    break;
            }

            if (piece != null) data.add(contact);

            System.out.println(Arrays.toString(contact));
            //readLine returns null if the end of the file has been reached
        } while (piece != null);

        addContactsToList(data);

    }

    private void addContactsToList(ArrayList<String[]> data) {
        observableList.clear();
        for (String[] contact : data) {
            observableList.addAll(new Contact(contact[0], contact[1], contact[2], contact[3]));
        }
    }

    //opens a FileChooser depending on 'what' parameter
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
        //set file path of the file containing contacts
        if (file != null) setPaths(file);
    }

    public void setPaths(File file) throws IOException {
        setContactsFile(file);
        //remove all contents of location.dat to keep it a one-liner
        BufferedWriter bw = Files.newBufferedWriter(location, StandardOpenOption.TRUNCATE_EXISTING);
        //update the path to contacts in location.dat
        bw.write(file.getAbsolutePath());
        bw.flush();
    }

    public void saveContactList(File file) throws IOException {
        //user pressed cancel
        if (file == null) return;
        //open contacts file for writing
        PrintWriter pwContacts = new PrintWriter(new BufferedWriter(new FileWriter(file)));

        //save contacts to a file
        for (Contact contact : observableList) {
            pwContacts.write(String.format("%s\n%s\n%s\n%s\n\n", contact.getFirstName(),
                    contact.getLastName(), contact.getPhoneNumber(), contact.getContactNotes()));
            pwContacts.flush();
        }
    }

    public void newContactList() throws IOException {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("New contacts list");
//        alert.setHeaderText("You are about to create a new contacts list.");
//        alert.setContentText("Do you want to proceed?");
//
//        Optional<ButtonType> result = alert.showAndWait();

        String title = "New contacts list";
        String header = "You are about to create a new contacts list.";
        String content = "Do you want to proceed?";

        Optional<ButtonType> result = Controller.alert(title, header, content);

        if (result.isPresent() && result.get() == ButtonType.OK) {

            saveContactList(contactsFile);
            setPaths(new File(defaultContacts));
            observableList.clear();
        }
    }
}
