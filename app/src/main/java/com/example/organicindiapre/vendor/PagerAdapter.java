package com.example.organicindiapre.vendor;
/*Aditya Kumar
 Used in OrderFragment

 */

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int noOfTabs;

    public PagerAdapter(FragmentManager fm, int noTabs) {
        super(fm);
        this.noOfTabs = noTabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0: Order_Subscription order_subsciption = new Order_Subscription();
                return order_subsciption;
            case 1: Order_OneTime order_onetime = new Order_OneTime();
                return order_onetime;
            default: return null;

        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
