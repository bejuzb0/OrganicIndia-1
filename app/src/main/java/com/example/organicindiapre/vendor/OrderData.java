package com.example.organicindiapre.vendor;

import com.google.firebase.Timestamp;

public class OrderData {
    String CustomerID;
    String VendorID;
    String ProductID;
    Timestamp From;
    Timestamp To;
    boolean Delivered;
    int Quantity;
    double Rate;

    public OrderData(String customerID, String vendorID, String productID, Timestamp from, Timestamp to, boolean delivered, int quantity, double rate) {
        CustomerID = customerID;
        VendorID = vendorID;
        ProductID = productID;
        From = from;
        To = to;
        Delivered = delivered;
        Quantity = quantity;
        Rate = rate;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getVendorID() {
        return VendorID;
    }

    public void setVendorID(String vendorID) {
        VendorID = vendorID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public Timestamp getFrom() {
        return From;
    }

    public void setFrom(Timestamp from) {
        From = from;
    }

    public Timestamp getTo() {
        return To;
    }

    public void setTo(Timestamp to) {
        To = to;
    }

    public boolean isDelivered() {
        return Delivered;
    }

    public void setDelivered(boolean delivered) {
        Delivered = delivered;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }
}