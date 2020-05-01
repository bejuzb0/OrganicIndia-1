package com.example.organicindiapre.vendor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.organicindiapre.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/*
    Aditya Kumar
    Uses class Order_Subscription and Order_OneTime, PagerAdapter
    The XML File for this is fragment_order_0.xmlml
    Mainly contains Generation of Tabs and ViewPager code
 */

public class OrderFragment extends Fragment implements Order_Subscription.onFragmentInteractionListener, Order_OneTime.onFragmentInteractionListener {

    public ArrayList<SelectedItems> selectedItems;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        selectedItems = new ArrayList<SelectedItems>();
        View rootView = inflater.inflate(R.layout.fragment_order_0,container,false);
        final TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Subscription"));
        tabLayout.addTab(tabLayout.newTab().setText("One Time"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Button delivered = (Button) rootView.findViewById(R.id.mark_delivery);

        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabLayout.getSelectedTabPosition() == 1) {
                    for (int i = 0; i < selectedItems.size(); i++) {

                    }
                }

            }
        });

        return rootView;

    }
}

