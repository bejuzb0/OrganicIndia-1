package com.example.organicindiapre;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;


public class Far_pending_requests extends Fragment
{


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pending_requests, container,false);

        Button activate = view.findViewById(R.id.activate_button);

        RecyclerView pendingReqRecyclerView = view.findViewById(R.id.pending_req_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        pendingReqRecyclerView.setLayoutManager(layoutManager);



        CustomerDetails[] customerDetails = new CustomerDetails[]{
                new CustomerDetails("sai","nuirvbrf ne","994378439"),
                new CustomerDetails("ravi","nuirvbrfkwewne","99433839549"),
                new CustomerDetails("sarif","sibiewbi","99433839549"),
        };
        PendingAndExistingReqAdapter adapter = new PendingAndExistingReqAdapter(customerDetails,"Pending",getContext());
        pendingReqRecyclerView.setAdapter(adapter);
        pendingReqRecyclerView.setHasFixedSize(true);

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "Activate selected list", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }




}
