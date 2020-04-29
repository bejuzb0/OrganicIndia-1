package com.example.organicindiapre;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Sai gopal Adapter for both pending and existing Subscription for both users
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class PendingAndExistingReqAdapter extends RecyclerView.Adapter<PendingAndExistingReqAdapter.viewholder>{

    private String fragmentName;
    private Activity context;
    private ArrayList<CustomerDetails> arrayList;
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private CollectionReference SubscriptionRef;

    PendingAndExistingReqAdapter(String fragmentName, Activity context, ArrayList<CustomerDetails> arrayList) {
        this.fragmentName = fragmentName;
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_subscription,parent,false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position)
    {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");

        SubscriptionRef = DB.collection("Subscriptions");
            SubscriptionRef
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            for (final QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()))
                            {
                                if (snapshot.getId().equals(arrayList.get(position).getSubscriptionID()))
                                {
                                    SubscriptionRef.document(snapshot.getId()).collection("Products")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                                {
                                                    for (QueryDocumentSnapshot snapshot1 : Objects.requireNonNull(task.getResult()))
                                                    {
                                                        String Name = Objects.requireNonNull(snapshot1.get("Name")).toString();
                                                        String Quantity =   Objects.requireNonNull(snapshot1.get("Quantity")).toString();
                                                        String Price = Objects.requireNonNull(snapshot1.get("Price")).toString();
                                                        holder.documentID = snapshot.getId();
                                                        ProductDetails details = new ProductDetails(Name,Quantity,Price);
                                                        holder.productDetailsArrayList.add(details);
                                                    }
                                                    final ProductAdapter ProductAdapter = new ProductAdapter(holder.productDetailsArrayList);
                                                    holder.ProductsRecyclerView.setHasFixedSize(true);
                                                    holder.ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                                                    holder.ProductsRecyclerView.setAdapter(ProductAdapter);

                                                }
                                            });

                                }

                            }

                        }
                    });




        holder.Cname.setText(arrayList.get(position).getCustomerName());
        holder.Cadd.setText(arrayList.get(position).getCustomerAddress()+" : "+arrayList.get(position).getCustomerPhoneNumber());
        holder.amount.setText(arrayList.get(position).getAmount());

        //Activate button for both activate  and save
        holder.activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               final String amount = holder.amount.getText().toString();
               if (!amount.isEmpty())
               {
                   if (fragmentName.equals("Pending"))
                   {
                       progressDialog.show();
                       progressDialog.setTitle("Updating");
                       Map<String ,Object> Update= new HashMap<>();
                       Update.put("Amount",amount);
                       Update.put("Status","Existing");
                       SubscriptionRef.document(holder.documentID)
                               .update(Update)
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task)
                           {
                               progressDialog.dismiss();
                               if (task.isSuccessful()){
                                   Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                   context.recreate();
                               }
                               else {
                                   Toast.makeText(context, ""+task.getException(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
                   }
                   else
                   {
                       SubscriptionRef.document(holder.documentID).update("Amount",amount);
                       Toast.makeText(context, "Amount saved", Toast.LENGTH_SHORT).show();
                   }
               }
               else {
                   Toast.makeText(context, "Enter amount", Toast.LENGTH_SHORT).show();
               }

            }
        });


        //managing Delete option
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Delete")
                        .setMessage("Are sure do you want to delete Subscription for customer : \n\n" +arrayList.get(position).getCustomerName())
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                progressDialog.setTitle("Deleting");
                                progressDialog.show();
                               SubscriptionRef.document(arrayList.get(position).getSubscriptionID())
                                       .delete()
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task)
                                           {
                                               progressDialog.dismiss();
                                               context.recreate();
                                           }
                                       });
                            }
                        })
                        .show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView Cname,Cadd;
        ImageButton delete;
        RecyclerView ProductsRecyclerView;
        ArrayList<ProductDetails> productDetailsArrayList;
        String documentID ;
        EditText amount ;
        Button activate;

        @SuppressLint("SetTextI18n")
        viewholder(@NonNull View itemView)
        {
            super(itemView);
            Cname = itemView.findViewById(R.id.customer_name);
            Cadd = itemView.findViewById(R.id.customer_adr_pho);
            delete = itemView.findViewById(R.id.delete_button);
            ProductsRecyclerView = itemView.findViewById(R.id.Products_recyclerView);
            productDetailsArrayList = new ArrayList<>();
            amount = itemView.findViewById(R.id.amount);
            activate = itemView.findViewById(R.id.activate_button);
            if (fragmentName.equals("Pending"))
            {
                delete.setVisibility(View.GONE);
                activate.setText("Activate");
            }
        }
    }



}
