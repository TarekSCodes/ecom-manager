package io.github.tarekscodes.models;

public class SupplierDTO {

    private String supplierName;
    private String supplierNumber;
    private String supplierStatus;
    private String supplierStreet;
    private String supplierStreetNumber;
    private String supplierPostalCode;
    private String supplierCity;
    private String supplierCountry;
    private String firstContactPhoneNumber;
    private String firstContactEmail;
    
    public  SupplierDTO() {
        // Default-Constructor
    }

    public SupplierDTO(String supplierName, String supplierNumber, String firstContactEmail, String supplierStatus) {
        this.supplierName = supplierName;
        this.supplierNumber = supplierNumber;
        this.firstContactEmail = firstContactEmail;
        this.supplierStatus = supplierStatus;
    }
    
    public String getFirstContactEmail() {
        return firstContactEmail;
    }
    public void setFirstContactEmail(String firstContactEmail) {
        this.firstContactEmail = firstContactEmail;
    }
    public String getFirstContactPhoneNumber() {
        return firstContactPhoneNumber;
    }
    public void setFirstContactPhoneNumber(String firstContactPhoneNumber) {
        this.firstContactPhoneNumber = firstContactPhoneNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getSupplierNumber() {
        return supplierNumber;
    }
    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }
    public String getSupplierStatus() {
        return supplierStatus;
    }
    public void setSupplierStatus(String supplierStatus) {
        this.supplierStatus = supplierStatus;
    }
    public String getSupplierStreet() {
        return supplierStreet;
    }
    public void setSupplierStreet(String supplierStreet) {
        this.supplierStreet = supplierStreet;
    }
    public String getSupplierStreetNumber() {
        return supplierStreetNumber;
    }
    public void setSupplierStreetNumber(String supplierStreetNumber) {
        this.supplierStreetNumber = supplierStreetNumber;
    }
    public String getSupplierPostalCode() {
        return supplierPostalCode;
    }
    public void setSupplierPostalCode(String supplierPostalCode) {
        this.supplierPostalCode = supplierPostalCode;
    }
    public String getSupplierCity() {
        return supplierCity;
    }
    public void setSupplierCity(String supplierCity) {
        this.supplierCity = supplierCity;
    }
    public String getSupplierCountry() {
        return supplierCountry;
    }
    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }


}