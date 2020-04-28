package com.example.organicindiapre;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
    *Sai Gopal
    *Used to Hold Customer Details
 */
@Keep
public class CustomerDetails  {

    private String CustomerName;
    private String CustomerAddress;
    private String CustomerPhoneNumber;
    private String CustomerUID;

    CustomerDetails(String customerName, String customerAddress, String customerPhoneNumber, String customerUID) {
        CustomerName = customerName;
        CustomerAddress = customerAddress;
        CustomerPhoneNumber = customerPhoneNumber;
        CustomerUID = customerUID;
    }

    CustomerDetails(String customerName, String address, String phoneNumber) {
        CustomerName = customerName;
        CustomerAddress = address;
        CustomerPhoneNumber = phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "CustomerDetails{" +
                "CustomerName='" + CustomerName + '\'' +
                ", Address='" + CustomerAddress + '\'' +
                ", PhoneNumber='" + CustomerPhoneNumber + '\'' +
                '}';
    }

    public CustomerDetails(String customerUID) {
        CustomerUID = customerUID;
    }

    public String getCustomerUID() {
        return CustomerUID;
    }

    public void setCustomerUID(String customerUID) {
        CustomerUID = customerUID;
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
