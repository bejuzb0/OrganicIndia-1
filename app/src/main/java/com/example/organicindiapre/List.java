package com.example.organicindiapre;


/**
 *Sai Gopal
 *Used to add both Vendor,Vendor Logo name and product name
 */

public class List {

    private String Name;
    private int VendorLogo;
    private String vendorID;

    public List(String Name, int vendorLogo, String vendorID) {
        this.Name = Name;
        VendorLogo = vendorLogo;
        this.vendorID = vendorID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }



    public List(String Name) {
        this.Name = Name;
    }

    public List() {

    }

    public String getVendorName() {
        return Name;
    }

    public void setVendorName(String Name) {
        Name = Name;
    }

    public int getVendorLogo() {
        return VendorLogo;
    }

    public void setVendorLogo(int vendorLogo) {
        VendorLogo = vendorLogo;
    }

    public List(String Name, int vendorLogo) {
        this.Name = Name;
        VendorLogo = vendorLogo;
    }
}
