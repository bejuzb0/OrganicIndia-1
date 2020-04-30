package com.example.organicindiapre.vendor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicindiapre.R;
import com.example.organicindiapre.UserDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private Button save;
    private Button addAddress;
    private TextView deliverytext,addresstext;

    private Spinner spinner;

    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String mName,mPhone;
    ArrayList<String> location;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      final  View view = inflater.inflate(R.layout.fragment_profile,container,false);



        final TextView cust_name = view.findViewById(R.id.profile_vend_actual_name);
        final TextView cust_phone_no = view.findViewById(R.id.profile_vend_actual_mobile_no);
        final TextView vend_delivery=view.findViewById(R.id.profile_vend_actual_delivery);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        final DocumentReference docRef =fStore.collection("Users")
                .document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    mName = documentSnapshot.getString("FirstName") + " "
                            + documentSnapshot.getString("LastName");
                    mPhone = fAuth.getCurrentUser().getPhoneNumber();

//for diplaying location-v
                    location=(ArrayList<String>)documentSnapshot.get("DeliveryLocation");
                    if(location!=null){
                        Log.d(TAG, location.toString());

                        for(int i=0;i<location.size();i++){
                            vend_delivery.append(location.get(i));
                            vend_delivery.append(", ");


                        }

                    }


                    cust_name.setText(mName);
                    cust_phone_no.setText(mPhone);
                }else {
                    Log.d(TAG, "Retrieving Data: Profile Data Not Found ");
                }
            }
        });


        save = view.findViewById(R.id.save_button_vend_profile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
            }
        });

        deliverytext=view.findViewById(R.id.textdelivery);
        addresstext=view.findViewById(R.id.addresstext);
        deliverytext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addAddress.setVisibility(view.VISIBLE);
                spinner.setVisibility(view.VISIBLE);
                addresstext.setVisibility(view.VISIBLE);


            }

        });

        //adding location to database-v
        addAddress=view.findViewById(R.id.add_vend_address);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String loc = spinner.getSelectedItem().toString();


                docRef.update("DeliveryLocation", FieldValue.arrayUnion(loc));


                Toast.makeText(getActivity(), "added : " + loc, Toast.LENGTH_SHORT)
                        .show();




                //Toast.makeText(getActivity(), "add Address", Toast.LENGTH_SHORT).show();
            }
        });





        return view;
        
    }
}
