package main;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private final SimpleStringProperty firstName = new SimpleStringProperty("");
    private final SimpleStringProperty lastName = new SimpleStringProperty("");
    private final SimpleStringProperty phoneNumber = new SimpleStringProperty("");
    private final SimpleStringProperty contactNotes = new SimpleStringProperty("");

    public Contact(String fN, String lN, String pN, String n) {
        setFirstName(fN);
        setLastName(lN);
        setPhoneNumber(pN);
        setContactNotes(n);
    }
    public Contact() {
        this("", "", "", "");
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setContactNotes(String notes) {
        this.contactNotes.set(notes);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getContactNotes() {
        return contactNotes.get();
    }
}
