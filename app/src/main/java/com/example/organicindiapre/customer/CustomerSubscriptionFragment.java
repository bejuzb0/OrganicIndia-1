package com.example.organicindiapre.customer;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organicindiapre.CustomerDetails;
import com.example.organicindiapre.ProductAdapter;
import com.example.organicindiapre.ProductDetails;
import com.example.organicindiapre.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class CustomerSubscriptionFragment extends Fragment
{
    final FirebaseFirestore FStore = FirebaseFirestore.getInstance();

    public ArrayList VendorUIDArrayList = new ArrayList<>();
    String UID;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_customer_subscription,container,false);

        final RecyclerView CustomerSubscriptionRecycler = view.findViewById(R.id.Customer_Subscription_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        CustomerSubscriptionRecycler.setLayoutManager(layoutManager);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final ArrayList<CustomerDetails> Customerdetails = new ArrayList<>();
        UID = requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        CollectionReference SubscriptionRef = FStore.collection("Subscriptions");

        SubscriptionRef
                .whereEqualTo("CustomerUID", Objects.requireNonNull(UID))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        progressDialog.dismiss();
                        for (final QueryDocumentSnapshot RootSnapshot : requireNonNull(task.getResult()))
                        {
                            final String VendorUID = requireNonNull(RootSnapshot.get("VendorUID")).toString();
                            FStore.collection("Users")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot snapshot : requireNonNull(task.getResult()))
                                            {
                                                if (snapshot.getId().equals(VendorUID))
                                                {
                                                    VendorUIDArrayList.add(VendorUID);
                                                        Customerdetails.add( new CustomerDetails
                                                                (
                                                                requireNonNull(snapshot.get("FirstName")).toString(),
                                                                requireNonNull(snapshot.get("Address")).toString(),
                                                                requireNonNull(snapshot.get("MobileNumber")).toString(),
                                                                RootSnapshot.getId()
                                                                ));
                                                }
                                            }
                                            CustomerSubscriptionAdapter adapter = new CustomerSubscriptionAdapter(Customerdetails);
                                            CustomerSubscriptionRecycler.setAdapter(adapter);
                                            CustomerSubscriptionRecycler.setHasFixedSize(true);
                                        }
                                    });
                        }

                    }
                });



        return view;
    }

    public  class CustomerSubscriptionAdapter extends RecyclerView.Adapter<CustomerSubscriptionAdapter.ViewHolder>{

        private ArrayList<CustomerDetails> customerDetailsArrayList;
        private CollectionReference SubscriptionRef;


        CustomerSubscriptionAdapter(ArrayList<CustomerDetails> customerDetailsArrayList) {
            this.customerDetailsArrayList = customerDetailsArrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_subscription_items,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
        {

            SubscriptionRef = FStore.collection("Subscriptions");
            SubscriptionRef
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            for (final QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()))
                            {
                                if (snapshot.getId().equals(customerDetailsArrayList.get(position).getSubscriptionID()))
                                {
                                    String Status = requireNonNull(snapshot.get("Status")).toString();
                                    holder.Status.setText(Status);
                                    if (Status.equals("Existing"))
                                    {
                                        holder.NoDelivery.setVisibility(View.VISIBLE);
                                    }
                                    SubscriptionRef.document(snapshot.getId()).collection("Products")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                                {
                                                    for (QueryDocumentSnapshot snapshot1 : Objects.requireNonNull(task.getResult()))
                                                    {
                                                        if (snapshot1.exists())
                                                        {
                                                            String Name = Objects.requireNonNull(snapshot1.get("Name")).toString();
                                                            String Quantity =   Objects.requireNonNull(snapshot1.get("Quantity")).toString();
                                                            String Price = Objects.requireNonNull(snapshot1.get("Price")).toString();
                                                            holder.documentID = snapshot.getId();
                                                            ProductDetails details = new ProductDetails(Name,Quantity,Price);
                                                            holder.productDetailsArrayList.add(details);
                                                        }

                                                    }
                                                    final ProductAdapter ProductAdapter = new ProductAdapter(holder.productDetailsArrayList);
                                                    holder.ProductsRecyclerView.setHasFixedSize(true);
                                                    holder.ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                    holder.ProductsRecyclerView.setAdapter(ProductAdapter);

                                                }
                                            });

                                }

                            }

                        }
                    });


            holder.VendorPhone.setText(customerDetailsArrayList.get(position).getCustomerPhoneNumber());
            holder.VendorAddress.setText(customerDetailsArrayList.get(position).getCustomerAddress());
            holder.VendorName.setText(customerDetailsArrayList.get(position).getCustomerName());

            holder.NoDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    
                    Map<String , Object> NoDeliveryDates = new HashMap<>();
                    NoDeliveryDates.put("From","3453");
                    NoDeliveryDates.put("To","345");
                    FStore.collection("Reports").document(String.valueOf(VendorUIDArrayList.get(position)))
                            .collection("Customers").document(UID).collection("NoDelivery").document()
                            .set(NoDeliveryDates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    Toast.makeText(getContext(), "Document added", Toast.LENGTH_SHORT).show();
                                }
                            });
                    Toast.makeText(getContext(), ""+VendorUIDArrayList.get(position), Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return customerDetailsArrayList.size();
        }

         class ViewHolder extends RecyclerView.ViewHolder
        {
            RecyclerView ProductsRecyclerView;
            ArrayList<ProductDetails> productDetailsArrayList;
            String documentID ;
            TextView VendorName,VendorAddress,VendorPhone,Status;
            Button NoDelivery;
            ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                VendorName = itemView.findViewById(R.id.vendor_name);
                VendorAddress = itemView.findViewById(R.id.vendor_address);
                VendorPhone = itemView.findViewById(R.id.vendor_number);
                Status = itemView.findViewById(R.id.status);
                productDetailsArrayList = new ArrayList<>();
                ProductsRecyclerView = itemView.findViewById(R.id.customer_subscription_products_recycler);
                NoDelivery = itemView.findViewById(R.id.no_delivery_button);
            }
        }
    }
}
