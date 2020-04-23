package com.example.organicindiapre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Far_manage_existing extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_existiing, container,false);

        RecyclerView ExistingRecyclerView = view.findViewById(R.id.pending_req_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ExistingRecyclerView.setLayoutManager(layoutManager);

        CustomerDetails[] customerDetails = new CustomerDetails[]{
                new CustomerDetails("Customer name 1","igrueosrbhjljhv ne","994378439"),
                new CustomerDetails("Customer name 2","nuirvbrfkwewne","99433839549"),
        };
        PendingAndExistingReqAdapter adapter = new PendingAndExistingReqAdapter(customerDetails,"Existing",getContext());
        ExistingRecyclerView.setAdapter(adapter);
        ExistingRecyclerView.setHasFixedSize(true);


        return view;
    }

}
