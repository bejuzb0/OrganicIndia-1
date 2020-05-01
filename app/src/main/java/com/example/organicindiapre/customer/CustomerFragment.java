package com.example.organicindiapre.customer;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.organicindiapre.R;
import com.example.organicindiapre.ViewPagerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CustomerFragment extends Fragment {
public String Add;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer,container,false);

        ViewPager viewPager = view.findViewById(R.id.viewPager_subscription);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);
        SupplierFragment supplierFragment = new SupplierFragment();
       ProductOrderFragment productOrderFragment = new ProductOrderFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        adapter.AddFragment(supplierFragment,"Supplier");
        adapter.AddFragment(productOrderFragment,"Product");
        viewPager.setAdapter(adapter);







        return view;

    }
}
