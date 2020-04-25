package com.example.organicindiapre.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.organicindiapre.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
/*Aditya Kumar

*/
public class CustomerProfileFragment extends Fragment {
    private DatabaseReference database;
    private FirebaseUser user;
    String userID;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerProfileFragment() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerProfileFragment newInstance(String param1, String param2) {
        CustomerProfileFragment fragment = new CustomerProfileFragment();
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
        final View view = inflater.inflate(R.layout.fragment_customer_profile, container, false);


        Button save = view.findViewById(R.id.save_button_cust_profile);
        Button update = view.findViewById(R.id.update_button_cust_profile);
        final TextView cust_name = view.findViewById(R.id.profile_cust_actual_name);
        final TextView cust_phone_no = view.findViewById(R.id.profile_cust_actual_mobile_no);
        final TextView cust_delivery_addr_name = view.findViewById(R.id.profile_cust_actual_del_address_name);
        final TextView cust_delivery_addr = view.findViewById(R.id.profile_cust_actual_del_address);
        final EditText cust_deliver_addr_name_update = view.findViewById(R.id.editTextDelName);
        final EditText cust_deliver_addr_addr_update = view.findViewById(R.id.editTextDelAddr);

        database = FirebaseDatabase.getInstance().getReference().child("User").child(userID);

       database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                String delAddrName = dataSnapshot.child("DeliveryAddressName").getValue().toString();
                String delAddr = dataSnapshot.child("DeliveryAddress").getValue().toString();
                String mobileNo = dataSnapshot.child("MobileNumber").getValue().toString();
                cust_name.setText(name);
                cust_phone_no.setText(mobileNo);
                cust_delivery_addr_name.setText(delAddrName);
                cust_delivery_addr.setText(delAddr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  cust_delivery_addr_name.setVisibility(View.GONE);
                cust_deliver_addr_addr_update.setVisibility(View.VISIBLE);
                cust_deliver_addr_name_update.setVisibility(View.VISIBLE);
                cust_delivery_addr.setVisibility(View.GONE);

            */
              Toast.makeText(getContext(), "Updating", Toast.LENGTH_SHORT).show();
            }

        });







        return view;
    }
}
