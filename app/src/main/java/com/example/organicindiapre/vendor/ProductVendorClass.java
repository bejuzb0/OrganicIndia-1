package com.example.organicindiapre.vendor;

public class ProductVendorClass {

    String ProductName;
    String productID;
    String Rate;
    String Quantity;

    public ProductVendorClass(String productName, String productID, String rate, String quantity) {
        ProductName = productName;
        this.productID = productID;
        Rate = rate;
        Quantity = quantity;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }



}
