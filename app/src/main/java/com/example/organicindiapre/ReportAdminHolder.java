package com.example.organicindiapre;

import com.example.organicindiapre.customer.ProductsAdapters;

import java.util.PriorityQueue;

public class ReportAdminHolder{

    private String VendorName;
    private String VendorPhoneNumber;
    private String CustomerName;
    private String CustomerPhoneNumber;
    private String ProductName;
    private String ProductPrice;
    private String ProductQuantity;
    private String Revenue;

    public ReportAdminHolder(String vendorName, String vendorPhoneNumber, String revenue) {
        VendorName = vendorName;
        VendorPhoneNumber = vendorPhoneNumber;
        Revenue = revenue;
    }

    public ReportAdminHolder(String vendorName, String vendorPhoneNumber, String customerName,
                             String customerPhoneNumber, String productName, String productPrice,
                             String productQuantity) {
        VendorName = vendorName;
        VendorPhoneNumber = vendorPhoneNumber;
        CustomerName = customerName;
        CustomerPhoneNumber = customerPhoneNumber;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductQuantity = productQuantity;
    }

    public ReportAdminHolder(String vendorName, String customerName, String productName, String productPrice, String productQuantity) {
        VendorName = vendorName;
        CustomerName = customerName;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductQuantity = productQuantity;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return CustomerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        CustomerPhoneNumber = customerPhoneNumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getVendorPhoneNumber() {
        return VendorPhoneNumber;
    }

    public void setVendorPhoneNumber(String vendorPhoneNumber) {
        VendorPhoneNumber = vendorPhoneNumber;
    }

    public String getRevenue() {
        return Revenue;
    }

    public void setRevenue(String revenue) {
        Revenue = revenue;
    }
}
