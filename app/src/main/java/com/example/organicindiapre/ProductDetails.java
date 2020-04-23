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
    private int ProductQuantity;
    private int ProductPrice;
    private int MinPackingQuantity;
   // private String QuantityType;

    public ProductDetails(String name, int productQuantity, int productPrice, int minPackingQuantity) {
        Name = name;
        ProductQuantity = productQuantity;
        ProductPrice = productPrice;
        MinPackingQuantity = minPackingQuantity;
      //  QuantityType = quantityType;
    }
    public ProductDetails(String name, int productQuantity, int productPrice) {
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

    public int getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public ProductDetails(){

    }

}
