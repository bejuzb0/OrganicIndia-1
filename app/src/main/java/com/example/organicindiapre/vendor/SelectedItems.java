package com.example.organicindiapre.vendor;

public class SelectedItems {
    String customerID;
    String vendorID;
    String productID;

    public String getCustomerID() {
        return customerID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public String getProductID() {
        return productID;
    }

    public SelectedItems(String customerID, String vendorID, String productID) {
        this.customerID = customerID;
        this.vendorID = vendorID;
        this.productID = productID;
    }
}