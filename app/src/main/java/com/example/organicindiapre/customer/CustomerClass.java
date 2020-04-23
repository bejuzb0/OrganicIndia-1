package com.example.organicindiapre.customer;
/*Aditya Kumar
    Used to Hold Customer Details Along with the products they have ordered
 */

import java.util.List;

public class CustomerClass {
    String cust_name;
    String address;
    String phone_no;
    List<CustProduct_Subclass> ProductList;
    public CustomerClass(String cust_name, String address, String phone_no, List<CustProduct_Subclass> ProductList) {
        this.cust_name = cust_name;
        this.address = address;
        this.phone_no = phone_no;
        this.ProductList = ProductList;
    }

    public String getCust_name() {
        return cust_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getAddress() {
        return address;
    }
    public List<CustProduct_Subclass> getProductList() {
        return ProductList;
    }
}
