package com.example.organicindiapre.vendor;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.organicindiapre.ItemAdapter;
import com.example.organicindiapre.ProductAdapter;
import com.example.organicindiapre.ProductDetails;
import com.example.organicindiapre.R;
import com.example.organicindiapre.customer.CustProduct_Subclass;
import com.example.organicindiapre.customer.CustomerClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Order_OneTime#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_OneTime extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db;
    ItemAdapter itemAdapter;
    CustomerClass customerClass;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    String customerfname, customerlname, address, mobilenumber;
    final Map<String, ArrayList<OrderDataOneTime>> getOrder;
    String prodname;
    double amount;
    List<CustProduct_Subclass> subclass;
    List<CustomerClass> obj;

    public Order_OneTime() {
        // Required empty public constructor
        db = FirebaseFirestore.getInstance();
        getOrder = new HashMap<String, ArrayList<OrderDataOneTime>>();
        subclass = new ArrayList<CustProduct_Subclass>();
        obj = new ArrayList<CustomerClass>();

    }

    public static Order_OneTime newInstance(String param1, String param2) {
        Order_OneTime fragment = new Order_OneTime();
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
        View rootView = inflater.inflate(R.layout.fragment_order__one_time, container, false);
        final RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycViewOneTime);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final List<CustomerClass> itemList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //Contains list of Customer that this vendor has
        final List<CustomerClass> customerlist = new ArrayList<CustomerClass>();

        CollectionReference users = db.collection("Users").document(user.getUid()).collection("Order_OneTime");

        //To uniquely get every customer
        final Map<String, Object> customerdetails = new HashMap<String, Object>();
        Log.d(TAG, user.getUid());
        db.collection("Orders_OneTime").whereEqualTo("VendorID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "Hellooo1");
                            for(QueryDocumentSnapshot doc: task.getResult()) {
                                Log.d(TAG, "Hellooo");
                                Log.d(TAG, doc.getId());
                                Map<String, Object> m = doc.getData();

                                String key = m.get("CustomerID").toString();
                                if(customerdetails.get(key) == null) {
                                    customerlist.add(new CustomerClass(m.get("Name").toString(), m.get("Address").toString(), m.get("MobileNumber").toString(), m.get("CustomerID").toString()));
                                }
                                customerdetails.put(key, new Object());
                            }

                            itemAdapter = new ItemAdapter(customerlist, getContext());
                            recyclerView.setAdapter(itemAdapter);
                            itemAdapter.notifyDataSetChanged();

                        }
                        else {

                        }
                    }
                });



        /*db.collection("Order_OneTime").whereEqualTo("VendorID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot doc: task.getResult()) {
                                Map<String, Object> m = doc.getData();
                                String key = m.get("CustomerID").toString();
                                OrderDataOneTime orderData = new OrderDataOneTime(m.get("CustomerID").toString(), m.get("VendorID").toString(), m.get("ProductID").toString(),  Boolean.parseBoolean(m.get("Delivered").toString()), Integer.parseInt(m.get("Quantity").toString()), Double.parseDouble(m.get("Rate").toString()));
                                if (getOrder.get(key) == null) {
                                    getOrder.put(key, new ArrayList<OrderDataOneTime>());
                                }
                                getOrder.get(key).add(orderData);
                            }
                            setRetrievedData();
                        }
                        else {

                        }
                    }
                });*/


      /*  users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        final Map<String, Object> m = document.getData();

                        List<String> productName = (List<String>) document.get("ProductName");
                        List<String> quant = (List<String>) document.get("Quantity");
                        List<String> amnt = (List<String>) document.get("Rate");


                        List<CustProduct_Subclass> subclass = new ArrayList<CustProduct_Subclass>();

                        for(int i=0; i<productName.size(); i++) {
                            subclass.add(new CustProduct_Subclass(productName.get(i).toString(), quant.get(i).toString(), amnt.get(i).toString(), "", "false"));
                        }
                        CustomerClass obj = new CustomerClass(m.get("CustomerName").toString(), m.get("Address").toString(), m.get("PhoneNumber").toString(), subclass);
                        Log.d(TAG, document.getId().toString());
                        customerlist.add(obj);
                    }
                    itemAdapter = new ItemAdapter(customerlist, getContext());
                    recyclerView.setAdapter(itemAdapter);
                    itemAdapter.notifyDataSetChanged();

                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });*/
        return rootView;
    }

    public interface onFragmentInteractionListener {
    }

    public void setRetrievedData() {
        Iterator hmIterator = getOrder.entrySet().iterator();

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            ArrayList<OrderDataOneTime> data = ((ArrayList<OrderDataOneTime>) mapElement.getValue());

            subclass.clear();
            for(int i=0; i< data.size(); i++) {

                db.collection("Products").document(data.get(i).getProductID())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                prodname = document.get("ProductName").toString();
                                amount = Double.parseDouble(document.get("Amount").toString());
                            }
                            else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

            }
            String key = (String)mapElement.getKey();


            db.collection("Users").document(key)
            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            customerfname = document.get("FirstName").toString();
                            customerlname = document.get("LastName").toString();
                            address = document.get("Address").toString();
                            mobilenumber = document.get("MobileNumber").toString();
                            obj.add(new CustomerClass(customerfname+" "+customerlname, address, mobilenumber, subclass));
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });



        }


    }
}


class OrderDataOneTime {
    String CustomerID;
    String VendorID;
    String ProductID;
    boolean Delivered;
    int Quantity;
    double Rate;

    public OrderDataOneTime(String customerID, String vendorID, String productID, boolean delivered, int quantity, double rate) {
        CustomerID = customerID;
        VendorID = vendorID;
        ProductID = productID;

        Delivered = delivered;
        Quantity = quantity;
        Rate = rate;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getVendorID() {
        return VendorID;
    }

    public void setVendorID(String vendorID) {
        VendorID = vendorID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public boolean isDelivered() {
        return Delivered;
    }

    public void setDelivered(boolean delivered) {
        Delivered = delivered;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }
}

