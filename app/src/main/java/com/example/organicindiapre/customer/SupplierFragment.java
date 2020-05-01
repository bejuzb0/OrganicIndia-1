package com.example.organicindiapre.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicindiapre.ItemAdapter;
import com.example.organicindiapre.List;
import com.example.organicindiapre.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SupplierFragment extends Fragment
{
    String mDAN;
    String Add;
    FirebaseFirestore db;

    ProductsAdapters adapter;
    private Spinner locality;


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String Addr;

    String Name,Address,MobileNumber,CustomerId;

    public SupplierFragment() {
        //this.Addr=Add;
        //Log.d(TAG,"n ADDRESS Addr"+Addr);
        db = FirebaseFirestore.getInstance();
    }






    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_order_cust,container,false);

        Button oneTime = view.findViewById(R.id.onetime);
        Button subscription = view.findViewById(R.id.subscrition);

        //Address(view);


        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Subscription", Toast.LENGTH_SHORT).show();
            }
        });

        //Log.d(TAG, "ADDRESS is "+Address(view));
        Log.d(TAG, "n ADDRESS is new "+Addr);

        final ArrayList<List> lis = new ArrayList<>();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final RecyclerView recyclerView = view.findViewById(R.id.vendor_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));





        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        final TextView cust_loc = view.findViewById(R.id.loc);

        DocumentReference docRef =fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Add = documentSnapshot.getString("Location");


                    //for delivery use
                    Name = documentSnapshot.getString("FirstName") + " " + documentSnapshot.getString("LastName");

                    MobileNumber = fAuth.getCurrentUser().getPhoneNumber();
                    CustomerId=fAuth.getCurrentUser().getUid();





                    //cust_loc.setText(Add);

                    //  Log.d(TAG, "ADDRESS........"+Add);
                }else {
                    Log.d(TAG, "Retrieving Data: Profile Data Not Found ");
                }
                db.collection("Users").whereEqualTo("UserType","Vendor")
                         .whereArrayContains("DeliveryLocation",Add)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG,"START");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // DocumentReference documentReference = document.getDocumentReference("Products");
                                        //  Log.d(TAG,"Try"+documentReference.toString());
                                        Map<String, Object> m = document.getData();
                                        lis.add(new List(m.get("FirstName").toString()+" "+m.get("LastName").toString(),
                                                R.drawable.ic_person, document.getId().toString()));
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getId().toString());
                                    }
                                    adapter = new ProductsAdapters(lis,getContext(),"supplier");
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();



                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });






        //final ProductsAdapters adapter = new ProductsAdapters(lis,getContext(),"supplier");
        //recyclerView.setAdapter(adapter);


        oneTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
               // Toast.makeText(getActivity(), "One Time", Toast.LENGTH_SHORT).show();
               showSelected("One Time",adapter);
            }
        });
        subscription.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Subscription", Toast.LENGTH_SHORT).show();
                showSelected("Subscription",adapter);
            }
        });

        return view;
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showSelected(final String title, final ProductsAdapters adapter)
    {
        if (adapter.getSelectedList() != null)
        {
            int arraySize = adapter.getSelectedList().size();
            if (arraySize > 0)
            {
                if (arraySize  < 6 )
                {
                    final String name= String.valueOf(adapter.getSelectedList().get(0));
                    final String quantity = String.valueOf(adapter.getSelectedList().get(1));
                    final String price = String.valueOf(adapter.getSelectedList().get(2));
                    final String vendorID=String.valueOf(adapter.getSelectedList().get(3));
                    final String productID= String.valueOf(adapter.getSelectedList().get(4));
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(title)
                        .setMessage("SELECTED ITEMS (" + arraySize + ") : \n"+adapter.getSelectedList().toString())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(getContext(),Delivery.class);

                                //passing data from fragment to delivery activity
                                intent.putExtra("ProductName",name);
                                intent.putExtra("Quantity",quantity);
                                intent.putExtra("Price",price);
                                intent.putExtra("VendorId",vendorID);
                                intent.putExtra("ProductId",productID);

                                intent.putExtra("Name",Name);
                                intent.putExtra("CustomerId",CustomerId);
                                intent.putExtra("MobileNumber",MobileNumber);
                                intent.putExtra("Address",Add);

                                //one time or subscription type
                                intent.putExtra("Type",title);
                                startActivity(intent);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Select One product at a time only", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(getActivity(), "Select Atleast One ", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Select Atleast One ", Toast.LENGTH_SHORT).show();
        }
    }



}
