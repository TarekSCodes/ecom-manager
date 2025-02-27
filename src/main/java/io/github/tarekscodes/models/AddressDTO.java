package io.github.tarekscodes.models;

public class AddressDTO {

    private int addressID;
    private String street;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String country;

    public AddressDTO() {
        // Default-Constructor
    }

    public int getAddressID() {
        return addressID;
    }
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getStreetNumber() {
        return streetNumber;
    }
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    
}
