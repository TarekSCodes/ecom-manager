package io.github.tarekscodes.models;

public class ContactPersonDTO {
    
    private int contactPersonID;
    private String firstName;
    private String lastName;
    private String email;
    private String phonePrefix;
    private String phoneNumber;
    private String faxNumber;

    public ContactPersonDTO() {}
    
    public ContactPersonDTO(int contactPersonID, String lastName) {

        this.contactPersonID = contactPersonID;
        this.lastName = lastName;
    }

    public int getContactPersonID() {
        return contactPersonID;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhonePrefix() {
        return phonePrefix;
    }
    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getFaxNumber() {
        return faxNumber;
    }
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

}
