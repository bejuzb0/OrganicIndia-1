package com.example.organicindiapre;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.example.organicindiapre.customer.CustProduct_Subclass;

import java.util.List;

/**
    *Sai Gopal
    *Used to Hold Customer Details
 */
@Keep
public class CustomerDetails  {

    private String CustomerName;
    private String CustomerAddress;
    private String CustomerPhoneNumber;


    @NonNull
    @Override
    public String toString() {
        return "CustomerDetails{" +
                "CustomerName='" + CustomerName + '\'' +
                ", Address='" + CustomerAddress + '\'' +
                ", PhoneNumber='" + CustomerPhoneNumber + '\'' +
                '}';
    }

    public CustomerDetails(String customerName, String address, String phoneNumber) {
        CustomerName = customerName;
        CustomerAddress = address;
        CustomerPhoneNumber = phoneNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerPhoneNumber() {
        return CustomerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber)
    {
        CustomerPhoneNumber = customerPhoneNumber;
    }

    public CustomerDetails(){

    }
}
