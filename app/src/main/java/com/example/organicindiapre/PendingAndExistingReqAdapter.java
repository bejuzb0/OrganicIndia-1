package com.example.organicindiapre;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Sai gopal Adapter for both pending and existing Subscription for both user and products
 * second adapter for products
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class PendingAndExistingReqAdapter extends RecyclerView.Adapter<PendingAndExistingReqAdapter.viewholder>{

    private String fragmentName;
    private Activity context;
    private ArrayList<CustomerDetails> arrayList;
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private CollectionReference product;
    private CollectionReference PendingProductRef,ExistingProductRef;

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

        final DocumentReference amountRef = DB.collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .collection("ExistingSubscription").document(arrayList.get(position).getCustomerUID());

        ExistingProductRef = DB.collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .collection("ExistingSubscription").document(arrayList.get(position).getCustomerUID())
                .collection("Products");

        //DocumentRef for
        PendingProductRef = DB.collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .collection("PendingSubscription").document(arrayList.get(position).getCustomerUID())
                .collection("Products");

        //Checking which fragment
        if (fragmentName.equals("Existing"))
        {
            product  = ExistingProductRef;
        }
        else {
            //data for products
            product  = PendingProductRef;

        }

        product.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()))
                        {
                            String Name = Objects.requireNonNull(snapshot.get("Name")).toString();
                            String Quantity =   Objects.requireNonNull(snapshot.get("Quantity")).toString();
                            String Price = Objects.requireNonNull(snapshot.get("Price")).toString();

                            ProductDetails details = new ProductDetails(Name,Quantity,Price);
                            holder.documentID = snapshot.getId();
                            holder.productDetailsArrayList.add(details);
                        }
                        final ProductAdapter ProductAdapter = new ProductAdapter(holder.productDetailsArrayList);
                        holder.ProductsRecyclerView.setHasFixedSize(true);
                        holder.ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        holder.ProductsRecyclerView.setAdapter(ProductAdapter);

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
               String amount = holder.amount.getText().toString();
               if (!amount.isEmpty())
               {
                   if (fragmentName.equals("Pending"))
                   {
                       PendingProductRef.get()
                               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                   @Override
                                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                       for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()))
                                       {
                                           moveFireStoreDocument(PendingProductRef.document(snapshot.getId()),
                                                   ExistingProductRef.document(snapshot.getId()));
                                       }
                                       DB.collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                               .collection("PendingSubscription").document(arrayList.get(position).getCustomerUID())
                                               .delete()
                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       if (task.isSuccessful())
                                                       {
                                                           Toast.makeText(context, "completed", Toast.LENGTH_SHORT).show();
                                                       }
                                                       else {
                                                           Toast.makeText(context, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               });

                                   }
                               });
                       Map<String ,Object> setAmount= new HashMap<>();
                       setAmount.put("Amount",amount);

                       amountRef.set(setAmount);
                   }
                   else
                   {
                      amountRef.update("Amount",amount);
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
                               DeleteSubscription(arrayList.get(position).getCustomerUID());
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
                amount.setText("Enter Final Amount");
            }
        }
    }


    public static class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder>{


        private ArrayList<ProductDetails> productDetailsArrayList;

        ProductAdapter(ArrayList<ProductDetails> productDetailsArrayList) {
            this.productDetailsArrayList = productDetailsArrayList;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_product_ist,parent,false);
            return new viewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final viewHolder holder, final int position)
        {
            final String Name = productDetailsArrayList.get(position).getName();
            final String Price = productDetailsArrayList.get(position).getProductPrice();
            final String Quantity = productDetailsArrayList.get(position).getProductQuantity();
            holder.Name.setText(Name);
            holder.Quantity.setText(Quantity);
            holder.Price.setText(Price);

        }


        @Override
        public int getItemCount() {
            return productDetailsArrayList.size();
        }

        static class viewHolder extends RecyclerView.ViewHolder{
            TextView Name,Quantity,Price;

            viewHolder(@NonNull View itemView) {
                super(itemView);
                Name = itemView.findViewById(R.id.product_name);
                Quantity = itemView.findViewById(R.id.product_quantity);
                Price = itemView.findViewById(R.id.product_amount);

            }
        }



    }

    private void DeleteSubscription(String CustomerUID)
    {
        DB.collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .collection("ExistingSubscription").document(CustomerUID)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            context.recreate();
                        }
                        else {
                            Toast.makeText(context, "Not Deleted"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void moveFireStoreDocument(final DocumentReference fromPath, final DocumentReference toPath)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        fromPath.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        try {
                            toPath.set(Objects.requireNonNull(document.getData()))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid)
                                        {
                                            Toast.makeText(context, "Document add successful", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });
                        }
                        catch (Exception e){
                            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}
