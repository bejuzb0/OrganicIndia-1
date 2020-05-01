package com.example.organicindiapre.vendor;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


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
import com.google.firebase.firestore.DocumentReference;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Order_OneTime extends Fragment {


    FirebaseFirestore db;
    ItemAdapter itemAdapter;
    CustomerClass customerClass;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Context c;

    String customerfname, customerlname, address, mobilenumber;
    //final Map<String, ArrayList<OrderDataOneTime>> getOrder;
    String prodname;
    double amount;
    List<CustProduct_Subclass> subclass;
    List<SelectedItems> selectedItems;
    List<CustomerClass> obj;
    Button delivery;

    public Order_OneTime(Context c) {
        // Required empty public constructor
        this.c = c;
        db = FirebaseFirestore.getInstance();
        // getOrder = new HashMap<String, ArrayList<OrderDataOneTime>>();
        subclass = new ArrayList<CustProduct_Subclass>();
        obj = new ArrayList<CustomerClass>();
        selectedItems = new ArrayList<SelectedItems>();


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_order__one_time, container, false);
        delivery = (Button) rootView.findViewById(R.id.mark_delivery_onetime);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycViewOneTime);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final List<CustomerClass> itemList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //Contains list of Customer that this vendor has
        final List<CustomerClass> customerlist = new ArrayList<CustomerClass>();

        //db.collection("Orders_OneTime").whereEqualTo("VendorID", user.getUid()).whereEqualTo("Delivered",false)

        //To uniquely get every customer
        final Map<String, Object> customerdetails = new HashMap<String, Object>();
        Log.d(TAG, user.getUid());
        db.collection("Orders_OneTime").whereEqualTo("VendorID", user.getUid()).whereEqualTo("Delivered", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Log.d(TAG, doc.getId());
                                Map<String, Object> m = doc.getData();

                                String key = m.get("CustomerID").toString();
                                if (customerdetails.get(key) == null) {
                                    CustomerClass obj = new CustomerClass(m.get("Name").toString(), m.get("Address").toString(), m.get("MobileNumber").toString(), m.get("CustomerID").toString());
                                    obj.setVendorID(user.getUid());
                                    customerlist.add(obj);
                                }
                                customerdetails.put(key, new Object());
                            }

                            itemAdapter = new ItemAdapter(customerlist, getContext(), selectedItems);
                            recyclerView.setAdapter(itemAdapter);
                            itemAdapter.notifyDataSetChanged();

                        } else {

                        }
                    }
                });

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItems.size() == 0)
                    Toast.makeText(c, "No item selected", Toast.LENGTH_SHORT).show();
                else {
                    Log.d(TAG, selectedItems.size() + "");
                    Toast.makeText(c, selectedItems.size() + " items delivered", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < selectedItems.size(); i++) {
                        Log.d(TAG, user.getUid());
                        Log.d(TAG, selectedItems.get(i).getCustomerID());
                        Log.d(TAG, selectedItems.get(i).getProductID());
                        db.collection("Orders_OneTime").whereEqualTo("VendorID", user.getUid()).whereEqualTo("CustomerID", selectedItems.get(i).getCustomerID()).whereEqualTo("ProductID", selectedItems.get(i).getProductID()).whereEqualTo("Delivered", false)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                                Log.d(TAG, doc.getId());
                                                Map<String, Object> m = new HashMap<String, Object>();
                                                m.put("Delivered", true);
                                                Log.d(TAG, doc.getId());
                                                db.collection("Orders_OneTime").document(doc.getId()).update(m);
                                            }
                                        } else {
                                        }
                                    }
                                });
                    }
                    //Refresh the UI here



                }
            }
        });

        return rootView;



    }

    public interface onFragmentInteractionListener {
    }
}