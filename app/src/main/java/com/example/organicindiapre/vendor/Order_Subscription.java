package com.example.organicindiapre.vendor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.organicindiapre.ItemAdapter;
import com.example.organicindiapre.R;
import com.example.organicindiapre.customer.CustProduct_Subclass;
import com.example.organicindiapre.customer.CustomerClass;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Order_Subscription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_Subscription extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Order_Subscription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_Subscription.
     */
    // TODO: Rename and change types and number of parameters
    public static Order_Subscription newInstance(String param1, String param2) {
        Order_Subscription fragment = new Order_Subscription();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order__subscription_0, container, false);
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycViewSubscription);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ItemAdapter itemAdapter = new ItemAdapter(buildItemList());
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;
    }
 // Code to generate 10 random Customer with each 3 products
    private List<CustomerClass> buildItemList() {
        List<CustomerClass> itemList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            CustomerClass customerClass = new CustomerClass("Customer "+ i, "Address"+i, "phoneNo"+i, buildProductList());
            itemList.add(customerClass);
        }
        return itemList;
    }

    private List<CustProduct_Subclass> buildProductList() {
        List<CustProduct_Subclass> subItemList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            CustProduct_Subclass subItem = new CustProduct_Subclass("Product "+i, "Quantity "+i, "Amount" +i);
            subItemList.add(subItem);
        }
        return subItemList;
    }

    public interface onFragmentInteractionListener {
    }
}
