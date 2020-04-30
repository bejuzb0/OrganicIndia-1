package com.example.organicindiapre.vendor;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicindiapre.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Sai Gopal Report fragment
 */
public class ReportFragment extends Fragment {

    private FirebaseFirestore DB;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_report_0, container,false);

        final RecyclerView noDelivery = view.findViewById(R.id.no_delivery_recycler);
        final RecyclerView revenueAmount = view.findViewById(R.id.Revenue_Amount_recycler);

        final ArrayList<ReportsHolder> noDeliveryArrayList = new ArrayList<>();
        final ArrayList<ReportsHolder> revenueArrayList = new ArrayList<>();

        DB = FirebaseFirestore.getInstance();

        String UID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final CollectionReference ReportRef = DB.collection("Reports").document(UID).collection("Customers");

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        LinearLayoutManager Layout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        noDelivery.setLayoutManager(linearLayout);
        revenueAmount.setLayoutManager(Layout);

        //Getting Data
        ReportRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (final QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                            {
                                if (snapshot.exists())
                                {
                                    DB.collection("Users")
                                            .document(snapshot.getId())
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    //Adding Amount and revenue to the adapter according to Customer
                                                    final ReportsHolder reportsHolder = new ReportsHolder(
                                                            Double.parseDouble((Objects.requireNonNull(snapshot.get("Amount"))).toString()),
                                                            Objects.requireNonNull(snapshot.get("Revenue")).toString(),
                                                            Objects.requireNonNull(documentSnapshot.get("FirstName")).toString()
                                                    );
                                                    revenueArrayList.add(reportsHolder);
                                                    RevenueAdapter revenueAdapter = new RevenueAdapter(revenueArrayList);
                                                    revenueAmount.setAdapter(revenueAdapter) ;
                                                    revenueAmount.setHasFixedSize(true);
                                                }
                                            });

                                    //Getting NoDelivery Data from Database
                                    ReportRef.document(snapshot.getId()).collection("NoDelivery")
                                            .orderBy("From")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                                {

                                                    if (task.isSuccessful()){
                                                        for (final QueryDocumentSnapshot snapshot1 : Objects.requireNonNull(task.getResult()))
                                                        {
                                                            //getting Users Name from Database
                                                            DB.collection("Users")
                                                                    .document(snapshot.getId())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful())
                                                                            {
                                                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                                                assert documentSnapshot != null;
                                                                                ReportsHolder reportsHolder = new ReportsHolder(
                                                                                        Objects.requireNonNull(snapshot1.get("From")).toString(),
                                                                                        Objects.requireNonNull(snapshot1.get("To")).toString(),
                                                                                        Objects.requireNonNull(documentSnapshot.get("FirstName")).toString()
                                                                                );
                                                                                noDeliveryArrayList.add(reportsHolder);
                                                                                NoDeliveryAdapter noDeliveryAdapter = new NoDeliveryAdapter(noDeliveryArrayList);
                                                                                noDelivery.setAdapter(noDeliveryAdapter);
                                                                                noDelivery.setHasFixedSize(true);
                                                                            }
                                                                        }
                                                                    });

                                                        }
                                                    }
                                                }
                                            });
                                }

                            }

                        }
                        else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }


    public static class NoDeliveryAdapter extends RecyclerView.Adapter<NoDeliveryAdapter.NoDeliveryViewHolder>
    {
        ArrayList<ReportsHolder> reportsHolders;

        NoDeliveryAdapter(ArrayList<ReportsHolder> reportsHolders) {
            this.reportsHolders = reportsHolders;
        }


        @NonNull
        @Override
        public NoDeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nodeliveryreport_items,parent,false);
            return new NoDeliveryViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull NoDeliveryViewHolder holder, int position) {

            holder.from.setText("From : " +reportsHolders.get(position).getFrom());
            holder.to.setText("To : "+reportsHolders.get(position).getTo());
            holder.customerName.setText(reportsHolders.get(position).getName());

        }

        @Override
        public int getItemCount() {
            return reportsHolders.size();
        }

        static class NoDeliveryViewHolder extends RecyclerView.ViewHolder {
            TextView from,to,customerName;
            NoDeliveryViewHolder(@NonNull View itemView) {
                super(itemView);
                from = itemView.findViewById(R.id.from);
                to = itemView.findViewById(R.id.to);
                customerName = itemView.findViewById(R.id.customer_name);
            }
        }
    }

    public static class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder>{

        ArrayList<ReportsHolder>  arrayList ;

        RevenueAdapter(ArrayList<ReportsHolder> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_amount_revenue_items,parent,false);
            return new RevenueViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position)
        {
            holder.Name.setText(arrayList.get(position).getName());
            holder.amount.setText("Amount : "+arrayList.get(position).getAmount());
            holder.revenue.setText("Revenue : "+arrayList.get(position).getRevenue());
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        static class RevenueViewHolder extends RecyclerView.ViewHolder {
            TextView revenue,amount,Name;
            RevenueViewHolder(@NonNull View itemView) {
                super(itemView);
                Name = itemView.findViewById(R.id.customer_name);
                revenue = itemView.findViewById(R.id.revenue);
                amount = itemView.findViewById(R.id.amount);
            }
        }
    }

}
