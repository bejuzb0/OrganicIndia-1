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
import com.google.android.gms.tasks.OnSuccessListener;
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


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_order__subscription_0, container, false);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final List<CustomerClass> itemList = new ArrayList<>();
        final RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycViewSubscription);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        final List<CustomerClass> customerlist = new ArrayList<CustomerClass>();
        final Map<String, ArrayList<OrderData>> getOrder = new HashMap<String, ArrayList<OrderData>>();
        CollectionReference users = db.collection("Users").document(user.getUid()).collection("Order_Subscription");
        /*db.collection("Order_Subscription").whereEqualTo("VendorID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot doc: task.getResult()) {
                                Map<String, Object> m = doc.getData();
                                String key = m.get("CustomerID").toString();
                                OrderData orderData = new OrderData(m.get("CustomerID").toString(), m.get("VendorID").toString(), m.get("ProductID").toString(), m.get("From"), m.get("To"), Boolean.parseBoolean(m.get("Delivered").toString()), Integer.parseInt(m.get("Quantity").toString()), Double.parseDouble(m.get("Rate").toString())));
                                if (getOrder.get(key) == null) {
                                    getOrder.put(key, new ArrayList<OrderData>());
                                }
                                getOrder.get(key).add(orderData);

                            }
                        }
                        else {

                        }
                    }
                }); */
        users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                    subclass.add(new CustProduct_Subclass(productName.get(i).toString(), quant.get(i).toString(),amnt.get(i).toString(), "", ""));
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
                });


        return rootView;
    }


    // Code to generate 10 random Customer with each 3 products
  /*  private List<CustomerClass> buildItemList() {
        List<CustomerClass> itemList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            CustomerClass customerClass = new CustomerClass("Customer "+ i, "Address"+i, "phoneNo"+i, buildProductList());
            itemList.add(customerClass);
        }
        return itemList;
    }
*/
  /*  private List<CustProduct_Subclass> buildProductList() {
        List<CustProduct_Subclass> subItemList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            CustProduct_Subclass subItem = new CustProduct_Subclass("Product "+i, "Quantity "+i, "Amount" +i, "Description"+i, "Delivered"+i);
            subItemList.add(subItem);
        }
        return subItemList;
    }

   */
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

