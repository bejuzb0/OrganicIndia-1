package com.example.organicindiapre.customer;
/*Aditya Kumar
    To easily generate variable number of products for a customer
    Used in CustomerClass
 */
public class CustProduct_Subclass {

    String product_name;
    String quantity;
    String amount;
    public CustProduct_Subclass(String product_name, String quantity, String amount) {
        this.product_name = product_name;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getProduct_name() {
        return product_name;
    }
    public String getQuantity() {
        return quantity;
    }
    public String getAmount() {
        return amount;
    }
}
