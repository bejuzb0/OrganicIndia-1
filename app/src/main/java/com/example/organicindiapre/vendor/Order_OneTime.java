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

    public Order_OneTime() {
        // Required empty public constructor
        db = FirebaseFirestore.getInstance();
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
        final List<CustomerClass> customerlist = new ArrayList<CustomerClass>();
        CollectionReference users = db.collection("Users").document(user.getUid()).collection("Order_OneTime");
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
                            subclass.add(new CustProduct_Subclass(productName.get(i).toString(), quant.get(i).toString(), amnt.get(i).toString(), "", ""));
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

    public interface onFragmentInteractionListener {
    }
}
