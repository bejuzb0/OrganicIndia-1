package com.example.organicindiapre.vendor;

public class ReportsHolder
{
    private Double Amount;
    private String Revenue;
    private String From;
    private String To;

    public String getSubscriptionUID() {
        return SubscriptionUID;
    }

    public void setSubscriptionUID(String subscriptionUID) {
        SubscriptionUID = subscriptionUID;
    }

    private String Name;
    private String SubscriptionUID;

    public ReportsHolder(String from, String to, String name, String subscriptionUID) {
        From = from;
        To = to;
        Name = name;
        SubscriptionUID = subscriptionUID;
    }

    public ReportsHolder(String from, String to, String name) {
        From = from;
        To = to;
        Name = name;
    }


    public ReportsHolder(Double amount, String revenue, String name) {
        Amount = amount;
        Revenue = revenue;
        Name = name;
    }


    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
