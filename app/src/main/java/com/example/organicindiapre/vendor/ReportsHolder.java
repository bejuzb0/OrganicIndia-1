package com.example.organicindiapre.vendor;

public class ReportsHolder
{
    private String Amount;
    private String Revenue;
    private String From;
    private String To;
    private String CustomerID;

    public ReportsHolder(String amount, String revenue, String from, String to, String customerID) {
        Amount = amount;
        Revenue = revenue;
        From = from;
        To = to;
        CustomerID = customerID;
    }
    public ReportsHolder(String amount, String revenue) {
        Amount = amount;
        Revenue = revenue;
    }
    public ReportsHolder(String from, String to, String customerID) {
        From = from;
        To = to;
        CustomerID = customerID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getRevenue() {
        return Revenue;
    }

    public void setRevenue(String revenue) {
        Revenue = revenue;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }
    public ReportsHolder()
    {

    }
}
