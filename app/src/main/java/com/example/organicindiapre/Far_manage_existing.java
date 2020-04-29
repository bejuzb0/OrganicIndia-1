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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

public class Far_manage_existing extends Fragment
{
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_existiing, container,false);

        final RecyclerView ExistingRecyclerView = view.findViewById(R.id.pending_req_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ExistingRecyclerView.setLayoutManager(layoutManager);

        final FirebaseFirestore FStore = FirebaseFirestore.getInstance();

        final ArrayList<CustomerDetails> Customerdetails = new ArrayList<>();
        String UID = requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        CollectionReference ExistingRef = FStore.collection("Users").document(UID)
                .collection("ExistingSubscription");


        ExistingRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                            for (final QueryDocumentSnapshot RootSnapshot : requireNonNull(task.getResult()))
                            {
                                Toast.makeText(getContext(), "" + RootSnapshot.getId(), Toast.LENGTH_SHORT).show();
                                FStore.collection("Users")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                                                for (QueryDocumentSnapshot snapshot : requireNonNull(task.getResult())) {
                                                    if (snapshot.getId().equals(RootSnapshot.getId())) {
                                                        CustomerDetails customerDetails = new CustomerDetails(
                                                                requireNonNull(snapshot.get("FirstName")).toString(),
                                                                requireNonNull(snapshot.get("Address")).toString(),
                                                                requireNonNull(snapshot.get("MobileNumber")).toString(),
                                                                snapshot.getId()
                                                        );
                                                        Toast.makeText(getContext(), ""+snapshot.getId(), Toast.LENGTH_SHORT).show();
                                                        Customerdetails.add(customerDetails);
                                                    }
                                                }
                                                PendingAndExistingReqAdapter adapter = new PendingAndExistingReqAdapter("Existing", getActivity(), Customerdetails);
                                                ExistingRecyclerView.setAdapter(adapter);
                                                ExistingRecyclerView.setHasFixedSize(true);
                                            }
                                        });
                            }
                    }
                });



        return view;
    }

}
