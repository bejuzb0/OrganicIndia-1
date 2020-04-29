package com.example.organicindiapre.vendor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.organicindiapre.ItemAdapter;
import com.example.organicindiapre.R;
import com.example.organicindiapre.customer.CustProduct_Subclass;
import com.example.organicindiapre.customer.CustomerClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductFragment extends Fragment {
    FirebaseFirestore db;
    ProductVendorClass obj;
    RecyclerView rv;
    List<ProductVendorClass> l;
    ProductAdapter pa;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_product_0,container,false);
        rv = (RecyclerView)rootview.findViewById(R.id.productRecylcerView);
        LinearLayoutManager lv = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lv);

        //registerForContextMenu(rv);

        l = new ArrayList<>();
        db = FirebaseFirestore.getInstance();


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference users = db.collection("Users").document(user.getUid()).collection("Products");
        users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        final Map<String, Object> m = document.getData();
                        Log.d(TAG, document.getId().toString());
                        obj = new ProductVendorClass(m.get("ProductName").toString(), document.getId().toString(), m.get("Rate").toString(), m.get("Quantity").toString());
                        l.add(obj);
                        m.clear();

                    }
                    pa = new ProductAdapter(l);
                    rv.setAdapter(pa);

                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
       /* for(int i=0; i<20; i++) {
            l.add(new ProductVendorClass("Product"+i, "ID"+i, "10"+i, "123+"+i));

        }*/

       Button add = (Button)rootview.findViewById(R.id.product_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Product");
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.add_product_vendor, (ViewGroup) getView(), false);
                final EditText name = (EditText) viewInflated.findViewById(R.id.product_name_add);
                final EditText quantity = (EditText) viewInflated.findViewById(R.id.product_quantity_add);
                final EditText rate = (EditText) viewInflated.findViewById(R.id.product_rate_add);
                builder.setView(viewInflated);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String prod_name = name.getText().toString();
                        String quant = quantity.getText().toString();
                        String ratee = rate.getText().toString();
                        l.add(new ProductVendorClass(prod_name, user.getUid()+prod_name, ratee,quant));
                        pa.notifyDataSetChanged();

                        Map<String, Object> product = new HashMap<>();
                        product.put("ProductName", prod_name);
                        product.put("Quantity", quant);
                        product.put("Rate", ratee);
                        product.put("ProductID", user.getUid()+prod_name);

                        db.collection("Users").document(user.getUid()).collection("Products").document(user.getUid()+prod_name)
                                .set(product)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });




        return rootview;
        
    }
}
