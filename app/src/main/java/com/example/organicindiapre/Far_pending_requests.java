package com.example.organicindiapre;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import static java.util.Objects.requireNonNull;


public class Far_pending_requests extends Fragment
{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pending_requests, container,false);


        final RecyclerView pendingReqRecyclerView = view.findViewById(R.id.pending_req_recyclerView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        pendingReqRecyclerView.setLayoutManager(linearLayout);

        final FirebaseFirestore FStore = FirebaseFirestore.getInstance();



        final ArrayList<CustomerDetails> Customerdetails = new ArrayList<>();
        String UID = requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        CollectionReference PendingRef = FStore.collection("Users").document(UID)
                .collection("PendingSubscription");

        PendingRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                assert queryDocumentSnapshots != null;
                if (queryDocumentSnapshots.getDocuments().isEmpty()) {
                    Toast.makeText(getContext(), "No records found", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (final DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        FStore.collection("Users")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        assert queryDocumentSnapshots != null;
                                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                            if (snapshot.getId().equals(document.getId())) {
                                                CustomerDetails customerDetails = new CustomerDetails(requireNonNull(snapshot.get("FirstName")).toString(), snapshot.getId(),
                                                        requireNonNull(snapshot.get("MobileNumber")).toString(),snapshot.getId());
                                                Customerdetails.add(customerDetails);
                                            }
                                        }
                                        PendingAndExistingReqAdapter adapter = new PendingAndExistingReqAdapter("Pending", getContext(), Customerdetails);
                                        pendingReqRecyclerView.setAdapter(adapter);
                                        pendingReqRecyclerView.setHasFixedSize(true);

                                    }
                                });
                    }
                }

            }
        });


        return view;
    }


}
