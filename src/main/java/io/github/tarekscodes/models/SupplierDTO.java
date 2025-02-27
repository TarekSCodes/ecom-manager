package io.github.tarekscodes.models;

import java.util.List;

public class SupplierDTO {

    private int supplierID;
    private String supplierName;
    private String supplierNumber;
    private String supplierStatus;
    private String firstContactPhoneNumber;
    private String firstContactEmail;
    private List<ContactPersonDTO> contactPersons;
    
    
    public  SupplierDTO() {
        // Default-Constructor
    }
    
    public SupplierDTO(int supplierID, String supplierName, String supplierNumber, String firstContactEmail, String supplierStatus) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierNumber = supplierNumber;
        this.firstContactEmail = firstContactEmail;
        this.supplierStatus = supplierStatus;
    }
    
    public int getSupplierID() {
        return supplierID;
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
}