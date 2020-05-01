package com.example.organicindiapre.customer;

import android.util.Log;

import static androidx.constraintlayout.widget.Constraints.TAG;

/*Aditya Kumar
    To easily generate variable number of products for a customer
    Used in CustomerClass
 */
public class CustProduct_Subclass {

    String ProductName;
    String Quantity;
    String Amount;
    String Description;
    String Delivered;

    public CustProduct_Subclass() {
    }

    public String getDelivered() {
        return Delivered;
    }

    public void setDelivered(String delivered) {
        Delivered = delivered;
    }

    public CustProduct_Subclass(String ProductName, String Quantity, String Amount, String Description, String Delivered) {
        Log.d(TAG, "CustomerSubClass Created");
        this.ProductName = ProductName;
        this.Quantity = Quantity;
        this.Amount = Amount;
        this.Delivered = Delivered;
        this.Description = Description;
    }



    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }



}
