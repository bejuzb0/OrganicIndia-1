package com.example.organicindiapre;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.example.organicindiapre.customer.CustProduct_Subclass;

import java.util.List;

/**
 *Sai Gopal
 *Used to Hold Product Details
 */

@Keep
public class ProductDetails  {

    private String Name;
    private String ProductQuantity;
    private String ProductPrice;
    private int MinPackingQuantity;
    private String vendorID;
    // private String QuantityType;

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public ProductDetails(String name, String productQuantity, String productPrice, int minPackingQuantity, String vendorID) {
        Name = name;
        ProductQuantity = productQuantity;
        ProductPrice = productPrice;
        MinPackingQuantity = minPackingQuantity;
        this.vendorID = vendorID;
        //  QuantityType = quantityType;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public ProductDetails(String name, String productQuantity, String productPrice) {
        Name = name;
        ProductQuantity = productQuantity;
        ProductPrice = productPrice;
    }


    public int getMinPackingQuantity() {
        return MinPackingQuantity;
    }

    public void setMinPackingQuantity(int minPackingQuantity) {
        MinPackingQuantity = minPackingQuantity;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public ProductDetails(){

    }

}