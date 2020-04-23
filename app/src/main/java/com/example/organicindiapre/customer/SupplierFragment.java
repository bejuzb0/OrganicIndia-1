package com.example.organicindiapre.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.organicindiapre.List;
import com.example.organicindiapre.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SupplierFragment extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_order_cust,container,false);

        Button oneTime = view.findViewById(R.id.onetime);
        Button subscription = view.findViewById(R.id.subscrition);


        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Subscription", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        List[] myListData = new List[]
                {
                        new List("SAI", R.drawable.ic_logout),
                        new List("Kricdk", R.drawable.ic_profile),
                        new List("GOPAL",R.drawable.ic_chat),
                        new List("abcd",R.drawable.ic_message),
                };

         */


        ArrayList<List> lis = new ArrayList<>();
        for (int i = 0;i<10;i++) {
            List list = new List("vendor name "+i, R.drawable.ic_person);
            lis.add(list);
        }

        RecyclerView recyclerView = view.findViewById(R.id.vendor_list);
        final ProductsAdapters adapter = new ProductsAdapters(lis,getContext(),"supplier");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


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
                if (arraySize  < 4 )
                {
                    final String name= String.valueOf(adapter.getSelectedList().get(0));
                    final String quantity = String.valueOf(adapter.getSelectedList().get(1));
                    final String price = String.valueOf(adapter.getSelectedList().get(2));
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(title)
                        .setMessage("SELECTED ITEMS (" + arraySize + ") : \n"+adapter.getSelectedList().toString())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(getContext(),Delivery.class);

                                //passing data from fragment to delivery activity
                                intent.putExtra("PN",name);
                                intent.putExtra("PQ",quantity);
                                intent.putExtra("PP",price);
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
