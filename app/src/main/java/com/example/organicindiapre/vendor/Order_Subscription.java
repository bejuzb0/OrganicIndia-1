package com.example.organicindiapre.vendor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.organicindiapre.ItemAdapter;
import com.example.organicindiapre.R;
import com.example.organicindiapre.customer.CustProduct_Subclass;
import com.example.organicindiapre.customer.CustomerClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Order_Subscription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_Subscription extends Fragment {

    FirebaseFirestore db;
    ItemAdapter itemAdapter;
    CustomerClass customerClass;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Order_Subscription() {
        db = FirebaseFirestore.getInstance();
    }
    
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

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order__subscription_0, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycViewSubscription);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(bu)
        recyclerView.setAdapter();

        /* Adding Customer Data in Recycler View from Firebase

        final List<CustomerClass> itemList = new ArrayList<>();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Log.d(TAG, user.getUid());

        CollectionReference users = db.collection("Users").document(user.getUid()).collection("Order_Subscription");
        /*users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                final Map<String, Object> m = document.getData();

                                /* Getting Subitem for the current Customer
                                Log.d(TAG, "Reached before subtask"+document.getId().toString());
                                Log.d(TAG, document.getId().toString());
                                CollectionReference orders = db.collection("Users").document(user.getUid()).collection("Order_Subscription").document(document.getId().toString()).collection("OrdersByCustomer");
                                final List<CustProduct_Subclass> subItemList = new ArrayList<>();
                                orders.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> subtask) {
                                                Log.d(TAG, "Reached inside subtask");
                                                if (subtask.isSuccessful()) {
                                                    for (QueryDocumentSnapshot subdocument : subtask.getResult()) {
                                                        Log.d(TAG, "DocumentSnapshot Subdata: " + subdocument.getData());

                                                        Map<String, Object> n = subdocument.getData();

                                                        Log.d(TAG,"Name"+n.get("ProductName").toString());
                                                        CustProduct_Subclass subItem = new CustProduct_Subclass(n.get("ProductName").toString(), n.get("Quantity").toString(), n.get("Amount").toString(), n.get("Description").toString(), n.get("Delivered").toString());
                                                        subItemList.add(subItem);
                                                        n.clear();
                                                    }
                                                    //Log.d(TAG,"Name"+m.get("CustomerName").toString());
                                                    Log.d(TAG, "sublistsize"+subItemList.size()+"");
                                                    if (m.get("CustomerName") != null && m.get("Address") != null && m.get("Address") != null && m.get("MobileNumber") != null)
                                                    {
                                                        customerClass = new CustomerClass(m.get("CustomerName").toString(), m.get("Address").toString(), m.get("MobileNumber").toString(), subItemList);
                                                        Log.d(TAG, customerClass.toString());
                                                        itemList.add(customerClass);
                                                    }


                                                } else {
                                                    Log.d(TAG,  "Reached inside subtask");
                                                    Log.d(TAG, "Error getting documents.", subtask.getException());
                                                }
                                            }
                                        });


                                /* Subitem part done

                                Log.d(TAG, "Finished Subtask");
                                m.clear();

                            }
                            Log.d(TAG, "listsize"+itemList.size()+"");
                            itemAdapter = new ItemAdapter(itemList);
                            recyclerView.setAdapter(itemAdapter);




                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });



       // ItemAdapter itemAdapter = new ItemAdapter(buildItemList());
        return rootView;
    }*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_order__subscription_0, container, false);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final List<CustomerClass> itemList = new ArrayList<>();

        CollectionReference users = db.collection("Users").document(user.getUid()).collection("Order_Subscription");
        users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                final Map<String, Object> m = document.getData();
                                Log.d(TAG, document.getId().toString());
                                customerClass = new CustomerClass(m.get("CustomerName").toString(), m.get("Address").toString(), m.get("MobileNumber").toString());
                                /*Code for subitem */
                                final List<CustProduct_Subclass> subItemList = new ArrayList<>();
                              /*  CollectionReference orders = db.collection("Users").document(user.getUid()).collection("Order_Subscription").document(document.getId().toString()).collection("OrdersByCustomer");


                                orders.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> subtask) {
                                        Log.d(TAG, "Reached inside subtask");
                                        if (subtask.isSuccessful()) {
                                            for (QueryDocumentSnapshot subdocument : subtask.getResult()) {
                                                Log.d(TAG, "DocumentSnapshot Subdata: " + subdocument.getData());
                                                Map<String, Object> n = subdocument.getData();
                                                Log.d(TAG,"Name"+n.get("ProductName").toString());
                                                CustProduct_Subclass subItem = new CustProduct_Subclass(n.get("ProductName").toString(), n.get("Quantity").toString(), n.get("Amount").toString(), n.get("Description").toString(), n.get("Delivered").toString());
                                                subItemList.add(subItem);
                                                n.clear();
                                            }
                                            //Log.d(TAG,"Name"+m.get("CustomerName").toString());
                                            Log.d(TAG, "sublistsize"+subItemList.size()+"");
                                            //if (m.get("CustomerName") != null && m.get("Address") != null && m.get("Address") != null && m.get("MobileNumber") != null)
                                           // {
                                                customerClass.setProductList(subItemList);
                                                Log.d(TAG, customerClass.toString());
                                                itemList.add(customerClass);
                                                //itemAdapter = new ItemAdapter(itemList);
                                                //recyclerView.setAdapter(itemAdapter);
                                            //}


                                        } else {
                                            Log.d(TAG,  "Reached inside subtask");
                                            Log.d(TAG, "Error getting documents.", subtask.getException());
                                        }
                                    }
                                });

                                */


                                customerClass.setProductList(buildProductList());
                                itemList.add(customerClass);
                                //customerClass = new CustomerClass(m.get("CustomerName").toString(), m.get("Address").toString(), m.get("MobileNumber").toString(), buildProductList());
                                Log.d(TAG, customerClass.toString());
                                //itemList.add(customerClass);
                                m.clear();

                            }

                            Log.d(TAG, "listsize"+itemList.size()+"");
                            final RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycViewSubscription);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            itemAdapter = new ItemAdapter(itemList);
                            recyclerView.setAdapter(itemAdapter);




                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });




        //ItemAdapter itemAdapter = new ItemAdapter(buildItemList());
        //recyclerView.setAdapter(itemAdapter);

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
            CustProduct_Subclass subItem = new CustProduct_Subclass("Product "+i, "Quantity "+i, "Amount" +i, "Description"+i, "Delivered"+i);
            subItemList.add(subItem);
        }
        return subItemList;
    }
 // Code to generate 10 random Customer with each 3 products
 /*   private List<CustomerClass> buildItemList() {


    }

    private List<CustProduct_Subclass> buildProductList(String userID) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference orders = db.collection("Users").document(user.getUid()).collection("Order_Subscription").document(userID).collection("OrdersByCustomer");
        final List<CustProduct_Subclass> subItemList = new ArrayList<>();

        orders.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "DocumentSnapshot Subdata: " + document.getData());
                                Map<String, Object> m = document.getData();
                                Log.d(TAG,"Name"+m.get("ProductName").toString());
                                CustProduct_Subclass subItem = new CustProduct_Subclass(m.get("ProductName").toString(), m.get("Quantity").toString(), m.get("Amount").toString(), m.get("Description").toString(), m.get("Delivered").toString());
                                subItemList.add(subItem);
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        Log.d(TAG, "sublistsize"+subItemList.size()+"");
        return subItemList;

    }

*/
    public interface onFragmentInteractionListener {
    }
}
